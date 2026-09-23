package wikipedia.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;

@DisplayName("Поиск в Wikipedia")
public class SearchTest extends TestBase {

    @ParameterizedTest(name = "Поиск \"{0}\" → проверка статьи: {1}")
    @CsvSource({
            "Spain,   Country in Southern and Western Europe",
            "France,  Country primarily in Western Europe",
            "Germany, Country in Europe"
    })
    public void searchCountryTest(String country, String description) {
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

            mainScreen.checkOpened();
        });

        step("Открыть поиск и закрыть подсказку, если появилась", () -> {
            mainScreen.clickSearch()
                    .closeTooltipIfVisible();
        });

        step("Ввести запрос \"" + country + "\" и кликнуть по первому сниппету", () -> {
            searchScreen
                    .typeQuery(country)
                    .clickFirstSnippet(country);
        });

        step("Проверить, что статья \"" + country + "\" открылась корректно", () -> {
            searchScreen
                    .checkFlagVisible()
                    .checkArticleTitle(country)
                    .checkArticleDescription(description);
        });
    }
}