package wikipedia.screens;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class SearchScreen {

    // Поле поиска
    private final SelenideElement searchField1 =
            $(AppiumBy.id("org.wikipedia.alpha:id/search_text_view"));
    private final SelenideElement searchField2 =
            $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"));

    // Флаг на странице статьи
    private final SelenideElement pageFlag =
            $(AppiumBy.id("org.wikipedia.alpha:id/view_page_header_image"));

    // Кнопка закрытия подсказки (крестик)
    private final SelenideElement closeTooltipButton =
            $(AppiumBy.accessibilityId("Close"));

    // ===== Действия =====
    @Step("Закрываем подсказку (если появилась)")
    public SearchScreen closeTooltipIfVisible() {
        try {
            closeTooltipButton
                    .shouldBe(visible, Duration.ofSeconds(2))
                    .click();
        } catch (Exception e) {
        }
        return this;
    }

    @Step("Вводим поисковый запрос: {query}")
    public SearchScreen typeQuery(String query) {
        searchField1.shouldBe(visible).click();
        searchField2.shouldBe(visible).setValue(query);
        return this;
    }

    @Step("Кликаем по первому сниппету: {country}")
    public SearchScreen clickFirstSnippet(String country) {
        $(AppiumBy.xpath("//android.widget.TextView[@text='" + country + "']"))
                .shouldBe(visible)
                .click();
        return this;
    }

    // Проверки
    @Step("Проверяем, что отображается флаг статьи")
    public SearchScreen checkFlagVisible() {
        pageFlag.shouldBe(visible);
        return this;
    }

    @Step("Проверяем заголовок статьи: {expected}")
    public SearchScreen checkArticleTitle(String expected) {
        $(AppiumBy.xpath("//android.view.View[@text='" + expected + "']"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(expected));
        return this;
    }

    @Step("Проверяем описание статьи: {expected}")
    public SearchScreen checkArticleDescription(String expected) {
        $(AppiumBy.xpath("//*[@text=\"" + expected + "\"]"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(expected));
        return this;
    }
}