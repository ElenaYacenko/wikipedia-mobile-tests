package wikipedia.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@DisplayName("Онбординг Wikipedia")
public class OnboardingTest extends TestBase {

    @Test
    @DisplayName("Прохождение 4 экранов онбординга с проверками")
    public void onboardingTest() {
        step("Пройти онбординг", () -> {
        onboarding
                .checkKnowledgeScreen()
                .clickForward()
                .checkDataPrivacyScreen()
                .clickForward()
                .checkLanguagesScreen()
                .clickForward()
                .checkCuriosityScreen()
                .clickSkip();
        });
        step("Проверить что открылся главный экран", () -> {
        mainScreen
                .checkOpened();
        });
    }
}
