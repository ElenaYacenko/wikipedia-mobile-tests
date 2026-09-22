package wikipedia.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wikipedia.pages.OnboardingScreen;

@DisplayName("Онбординг Wikipedia")
public class OnboardingTest extends TestBase{
    private final OnboardingScreen onboarding = new OnboardingScreen();

    @Test
    @DisplayName("Прохождение 4 экранов онбординга с проверками")
    public void onboardingTest() {
        onboarding
                .checkKnowledgeScreen()
                .clickForward()
                .checkDataPrivacyScreen()
                .clickForward()
                .checkLanguagesScreen()
                .clickForward()
                .checkCuriosityScreen()
                .clickSkip()
                .checkMainScreenOpened();
    }
}
