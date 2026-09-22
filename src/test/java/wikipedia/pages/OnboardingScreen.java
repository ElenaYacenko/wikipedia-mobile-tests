package wikipedia.pages;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class OnboardingScreen {
    // Экран № 1
    private final SelenideElement titleKnowledge =
            $(AppiumBy.xpath("//android.widget.TextView[@text=concat('All the world', \"'\", 's knowledge')]"));

    private final SelenideElement imageWikipedia =
            $(AppiumBy.accessibilityId("Wikipedia"));

    // Экран № 2
    private final SelenideElement titleDataPrivacy =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Data & Privacy']"));

    // Экран № 3
    private final SelenideElement titleLanguages =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Read in more than 300 languages']"));

    // Экран № 4
    private final SelenideElement titleCuriosity =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Follow your curiosity']"));

    // Кнопки
    private final SelenideElement forwardButton = $(AppiumBy.accessibilityId("Forward"));
    private final SelenideElement nextButton = $(AppiumBy.accessibilityId("Next"));
    private final SelenideElement skipButton =
            $(AppiumBy.xpath("//android.widget.TextView[@text='Skip']"));

    // Главный экран
    private final SelenideElement searchButton = $(AppiumBy.accessibilityId("Search"));

    // Действия

    public OnboardingScreen checkKnowledgeScreen() {
        titleKnowledge.shouldBe(visible).shouldHave(text("All the world's knowledge"));
        return this;
    }

    public OnboardingScreen checkDataPrivacyScreen() {
        titleDataPrivacy.shouldBe(visible).shouldHave(text("Data & Privacy"));
        return this;
    }

    public OnboardingScreen checkLanguagesScreen() {
        titleLanguages.shouldBe(visible).shouldHave(text("Read in more than 300 languages"));
        return this;
    }

    public OnboardingScreen checkCuriosityScreen() {
        titleCuriosity.shouldBe(visible).shouldHave(text("Follow your curiosity"));
        return this;
    }

    public OnboardingScreen clickForward() {
        forwardButton.shouldBe(visible).click();
        return this;
    }

    public OnboardingScreen clickNext() {
        nextButton.shouldBe(visible).click();
        return this;
    }

    public OnboardingScreen clickSkip() {
        skipButton.shouldBe(visible).click();
        return this;
    }

    public OnboardingScreen checkMainScreenOpened() {
        searchButton.shouldBe(visible);
        return this;
    }
}
