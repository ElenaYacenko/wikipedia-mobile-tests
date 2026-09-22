package wikipedia.drivers;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import wikipedia.config.TestConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class DriverFactory {

    public static AndroidDriver createDriver() {
        String deviceHost = System.getProperty("deviceHost", "emulation");
        System.setProperty("env", deviceHost);

        TestConfig config = ConfigFactory.create(TestConfig.class,
                System.getProperties(), System.getenv());

        AndroidDriver driver = switch (deviceHost) {
            case "emulation", "real" -> createLocalDriver(config);
            case "browserstack" -> createBrowserStackDriver(config);
            default -> throw new IllegalArgumentException("Unknown deviceHost: " + deviceHost);
        };

        // Регистрация драйвера в Selenide — критично для $(...) локаторов
        WebDriverRunner.setWebDriver(driver);
        return driver;
    }

    private static AndroidDriver createLocalDriver(TestConfig config) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(config.platformName())
                .setPlatformVersion(config.platformVersion())
                .setDeviceName(config.deviceName())
                .setUdid(config.udid())
                .setAutomationName(config.automationName())
                .setAppPackage(config.appPackage())
                .setAppActivity(config.appActivity())
                .setNoReset(config.noReset())
                .setApp(Paths.get(config.localApp()).toAbsolutePath().toString());

        return createDriver(config.appiumUrl(), options);
    }

    private static AndroidDriver createBrowserStackDriver(TestConfig config) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion(config.androidOsVersion())
                .setDeviceName(config.androidDeviceName())
                .setAutomationName("UiAutomator2")
                .setApp(config.androidApp())
                .setNoReset(false);

        String url = "https://" + config.browserstackUser() + ":" + config.browserstackKey()
                + "@hub.browserstack.com/wd/hub";
        return createDriver(url, options);
    }

    private static AndroidDriver createDriver(String url, UiAutomator2Options options) {
        try {
            return new AndroidDriver(new URL(url), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + url, e);
        }
    }
}