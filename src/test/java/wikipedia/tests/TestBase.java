package wikipedia.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import wikipedia.drivers.AndroidDriverProvider;
import wikipedia.screens.MainScreen;
import wikipedia.screens.OnboardingScreen;
import wikipedia.screens.SearchScreen;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    protected OnboardingScreen onboarding = new OnboardingScreen();
    protected MainScreen mainScreen = new MainScreen();
    protected SearchScreen searchScreen = new SearchScreen();


    @BeforeAll
    static void beforeAll() {
        if (blank(System.getProperty("deviceHost"))) {
            System.setProperty("deviceHost", "emulation");
        }

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        Configuration.pageLoadTimeout = -1;
        Configuration.browser = AndroidDriverProvider.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
        Configuration.screenshots = true;
        Configuration.savePageSource = false;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
        open();
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    protected void passOnboarding() {
        onboarding
                .checkKnowledgeScreen()
                .clickForward()
                .checkDataPrivacyScreen()
                .clickForward()
                .checkLanguagesScreen()
                .clickForward()
                .checkCuriosityScreen()
                .clickSkip();

        mainScreen.checkOpened();
    }
}