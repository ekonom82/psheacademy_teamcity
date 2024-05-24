package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.element;

public abstract class Page {
    // на многих страницах присутствуют кнопки, типа Login, Proced, пусть и с разным названием, но с одинаковым смыслом и соответсвенно, с одинаковым атрибутом
    private final SelenideElement submitButton = element(Selectors.byType("submit"));
    // элемент прогресса, который общий для всех действий, которые требуют время
    private final SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
    private final SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));

    // вынесем нажатие на кнопку типа Login/Proced, которые практически на каждой странице, в отдельный базовый функционал
    // !!! нажатие на данную кнопку и последующий прогресс ожидания всегда в связке идут в данном проекте, то в отдельный функционал (метод)
    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(Condition.visible, Duration.ofMinutes(1));
    }

    public void waitUntilDataIsSaved() {
        // !!! внутри всех методо should находится assertion, что означает, что если условие не выполняется, то тест упадет на этом этапе
        // а чем раньше упадем, тем быстрее отловим и починим проблему
        savingWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(30));
    }

    // трансформируем список вэбэлементов в список элементов (объектов)
    // каждый вэбэлемент из переданного списка передаем в конструктор, для создания объекта, который ложим в список и в конце возвращаем
//    public List<ProjectElement> generatePageElements(ElementsCollection collection) {
//        var elements = new ArrayList<ProjectElement>();
//
//        collection.forEach(webElement -> {
//            var pageElement = new ProjectElement(webElement);
//            elements.add(pageElement);
//        });
//
//        return elements;
//    }
    public  <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
