# go up to main work project dir
cd ..

## create variables
# our root dir of our project which is $(pwd) after go one step up (cd ..) from current dir (infra)
teamcity_tests_directory=$(pwd)
# dir where will be create infrastructure for all our future necessary containers
workdir="teamcity_tests_infrastructure"
# dir names where will be run all our specific container for relative specific dir
teamcity_server_workdir="teamcity_server"
teamcity_agent_workdir="teamcity_agent"
selenoid_workdir="selenoid"
# container names which will be used
teamcity_server_container_name="teamcity_server_instance"
teamcity_agent_container_name="teamcity_agent_instance"
selenoid_container_name="selenoid_instance"
selenoid_ui_container_name="selenoid_ui_instance"

# create list of dir names which we will use for creating dirs in $workdir and then run there our containers
workdir_names=($teamcity_server_workdir $teamcity_agent_workdir $selenoid_workdir)

# create list of container names which we will use for stopping, removing ang running containers
container_names=($teamcity_server_container_name $teamcity_agent_container_name $selenoid_container_name $selenoid_ui_container_name)

################################
echo "Request IP"
# get the list of all ips
export ips=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1')
# get first ip from the list $ips by splitting using character of "new string"
export ip=${ips%%$'\n'*}
echo "Current IP: $ip"

################################
echo "Delete previous run data"

# remove our work derectory recursive removing all its data
# and then creating work directory again and go in it
rm -rf $workdir
mkdir $workdir
cd $workdir

# create directories from the list for next runnig containers there
for dir in "${workdir_names[@]}"; do
  mkdir $dir
done

# removing before running conteiners by their names from the list of container names
for container in "${container_names[@]}"; do
  docker stop $container
  docker rm $container
done

################################
echo "Start teamcity server"

cd $teamcity_server_workdir

# run teamcity_server_container on 8111 port and with creating /logs dir for logs
docker run -d --name $teamcity_server_container_name  \
    -v $(pwd)/logs:/opt/teamcity/logs  \
    -p 8111:8111 \
    jetbrains/teamcity-server

echo "Teamcity Server is running..."

################################
echo "Start selenoid"
# !!! Before all for this step we need to create file brosers.json in $teamcity_tests_directory/infra/ which we will copy to config directory which will be created in $selenoid_workdir

cd .. && cd $selenoid_workdir
# create config directory inside $selenoid_workdir
# and then coppying there our brosers.json from $teamcity_tests_directory/infra/brosers.json
mkdir config
cp $teamcity_tests_directory/infra/brosers.json config/

# run $selenoid_container_name on port 4444 and where we send list of browsers from $selenoid_workdir/config/brosers.json
docker run -d                                   \
            --name $selenoid_container_name                                 \
            -p 4444:4444                                    \
            -v /var/run/docker.sock:/var/run/docker.sock    \
            -v $(pwd)/config/:/etc/selenoid/:ro              \
    aerokube/selenoid:latest-release

# Before running test we need to pull images of browsers which enumerate in browsers.json with their versions
# so we need to parse name of browsers from browsers.json automaticaly in list of browser (image) names
# we use Regex -> get text after "image": from file $(pwd)/config/browsers.json
image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/config/browsers.json"))

echo "Pull all browser images: $image_names"

# pull all necessary bowser images using list of browser names which we got just now by parsing browsers.json
for image in "${image_names[@]}"; do
  docker pull $image
done

################################
echo "Start selenoid-ui"

# run $selenoid_ui_container_name on port 80 and setting selenoid-uri using ip which we got in the first step and its port 4444 which set when we set run of $selenoid_container_name
docker run -d--name $selenoid_ui_container_name                                 \
            -p 80:8080 aerokube/selenoid-ui:latest-release --selenoid-uri "http://$ip:4444"

################################
echo "Setup teamcity server"

# run test method - setupTeamCityServerTest from test class - SetupFirstStartTest
# in this test method we make all necessary preparation activities when TeamCity project was up
mvn clean test -Dtest=SetupFirstStartTest#setupTeamCityServerTest

################################
echo "Parse superuser token"

# we use Regex (grep) -> get text after 'Super user authentication token: ' -> get everythink till we faced any number -> from file $teamcity_tests_directory/infra/$workdir/$teamcity_server_workdir/logs/teamcity-server.log
superuser_token=$(grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/infra/$workdir/$teamcity_server_workdir/logs/teamcity-server.log | awk '{print $NF}')
echo "Super user token: $superuser_token"

################################
echo "Setting config.properties"

# we set resources/config.properties in our project with data which we got in previous steps in this file
# we create a settings like text split every new variaples with character of new string "\n" and then save it in resources/config.properties
echo "host=$ip:8111\nsuperUserToken=$superuser_token\nremote=http://$ip:4444/wd/hub\nbrowser=firefox" > $teamcity_tests_directory/src/main/resources/config.properties
# show our created resources/config.properties with data
cat $teamcity_tests_directory/src/main/resources/config.properties

################################
echo "Start teamcity agent"

cd .. && cd $teamcity_agent_workdir

# run teamcity_agent_container_name with url by $ip and on 8111 port
docker run -e SERVER_URL="http://$ip:8111" -u 0 -d --name $teamcity_agent_container_name \
          -v $(pwd)/tmp/teamcity_agent/conf:/data/teamcity_agent/conf \
          jetbrains/teamcity-agent:2023.11.1

echo "Teamcity agent is running..."

################################
echo "Setup teamcity agent"

# run test method - setupTeamCityAgentTest from test class - SetupFirstStartTest
# in this test method we authorize before added unauthorized agent
mvn clean test -Dtest=SetupFirstStartTest#setupTeamCityAgentTest

################################
echo "Run system tests"

echo "Run API tests"
# we create testng-suites directory in the root dir of project where added xml-files which describe our suits of test
# we set that suit file for running using Maven
# !!!!!
# to connect our testng (our xml-suits-files) with Maven we need to add <suiteXmlFiles> block to pom.xml into <build><plugins><plugin><configuration> after all other blogs
mvn test -DsuiteXmlFile=testng-suites/api-suite.xml

echo "Run UI tests"
mvn test -DsuiteXmlFile=testng-suites/ui-suite.xml
