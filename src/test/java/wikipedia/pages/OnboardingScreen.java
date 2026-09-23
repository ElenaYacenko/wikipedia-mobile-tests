package wikipedia.pages;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class OnboardingScreen {

    // ===== Экран 1 =====
    private final SelenideElement titleKnowledge =
            $(AppiumBy.xpath("//android.widget.TextView[@text=concat('All the world', \"'\", 's knowledge')]"));

    // ===== Экран 2 =====
    private final SelenideElement titleDataPrivacy =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Data & Privacy']"));

    // ===== Экран 3 =====
    private final SelenideElement titleLanguages =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Read in more than 300 languages']"));

    // ===== Экран 4 =====
    private final SelenideElement titleCuriosity =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Follow your curiosity']"));

    // ===== Кнопки =====
    private final SelenideElement forwardButton = $(AppiumBy.accessibilityId("Forward"));
    private final SelenideElement nextButton = $(AppiumBy.accessibilityId("Next"));
    private final SelenideElement skipButton =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Skip']"));

    // Действия

    @Step("Нажимаем кнопку \"Forward\"")
    public OnboardingScreen clickForward() {
        forwardButton.shouldBe(visible).click();
        return this;
    }

    @Step("Нажимаем кнопку \"Next\"")
    public OnboardingScreen clickNext() {
        nextButton.shouldBe(visible).click();
        return this;
    }

    @Step("Нажимаем кнопку \"Skip\" (переход на главный экран)")
    public MainScreen clickSkip() {
        skipButton.shouldBe(visible).click();
        return new MainScreen();
    }

    // Проверки
    @Step("Проверяем заголовок экрана 1: \"All the world's knowledge\"")
    public OnboardingScreen checkKnowledgeScreen() {
        titleKnowledge.shouldBe(visible).shouldHave(text("All the world's knowledge"));
        return this;
    }

    @Step("Проверяем заголовок экрана 2: \"Data & Privacy\"")
    public OnboardingScreen checkDataPrivacyScreen() {
        titleDataPrivacy.shouldBe(visible).shouldHave(text("Data & Privacy"));
        return this;
    }

    @Step("Проверяем заголовок экрана 3: \"Read in more than 300 languages\"")
    public OnboardingScreen checkLanguagesScreen() {
        titleLanguages.shouldBe(visible).shouldHave(text("Read in more than 300 languages"));
        return this;
    }

    @Step("Проверяем заголовок экрана 4: \"Follow your curiosity\"")
    public OnboardingScreen checkCuriosityScreen() {
        titleCuriosity.shouldBe(visible).shouldHave(text("Follow your curiosity"));
        return this;
    }
}