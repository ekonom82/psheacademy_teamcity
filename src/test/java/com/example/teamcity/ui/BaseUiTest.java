package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.api.config.Config;
import org.testng.annotations.BeforeSuite;

/*
* будет родителем для всех последующих. В нем будет установлена конфигурация для нашего запуска теста для браузера, расширения,
* где сам браузер лежит и так далее. В этом поможет библиотека selenide - это обертка над selenium, которая позволяет более удобно делать
* те же самые проверки.
* */
public class BaseUiTest {

    /*
    * настройка конфигураций перед всеми тестами (в TestNG тесты запускаются по СЬЮТАМ): создаем настройку нашего Selenide, через его класс Configuration
    * */
    @BeforeSuite
    public void setupTests() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
    }
}
