package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

/*
* страница Проектов, когда мы кликаем по разделю верхнего меню Projects
* */
public class ProjectsPage extends FavoritesPage {
    // полный адрес страницы http://localhost:8111/favorite/projects?mode=builds
    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";

    // список наших проектов, что находятся на странице Projects (Favourite Projects), и имеют общий атрибут: class=Subproject__container--WE
    // будем данный список вэбэлементов трансформировать в список объектов в методе generatePageElements класса Page
    private final ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--Px"));

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubprojects() {
//        return generatePageElements(subprojects);
        return generatePageElements(subprojects, ProjectElement::new);
    }
}
