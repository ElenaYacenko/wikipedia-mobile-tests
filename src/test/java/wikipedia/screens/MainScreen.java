package wikipedia.screens;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class MainScreen {

    private final SelenideElement searchButton = $(AppiumBy.accessibilityId("Search"));

    // Действия
    @Step("Открываем экран поиска")
    public SearchScreen clickSearch() {
        searchButton.shouldBe(visible).click();
        return new SearchScreen();
    }

    // Проверки
    @Step("Проверяем, что главный экран открылся")
    public MainScreen checkOpened() {
        searchButton.shouldBe(visible);
        return this;
    }
}