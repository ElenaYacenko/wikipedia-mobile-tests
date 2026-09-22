package wikipedia.drivers;

import com.codeborne.selenide.WebDriverProvider;
import wikipedia.config.TestConfig;
import io.appium.java_client.android.AndroidDriver;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AndroidDriverProvider implements WebDriverProvider {

    @Override
    public WebDriver createDriver(Capabilities ignored) {
        String host = System.getProperty("deviceHost", "emulation");
        System.setProperty("env", host);
        TestConfig config = ConfigFactory.create(
                TestConfig.class, System.getProperties(), System.getenv());

        // Отладка
        System.out.println(">>> host  = " + host);
        System.out.println(">>> env   = " + System.getProperty("env"));
        System.out.println(">>> user  = " + config.browserstackUser());
        System.out.println(">>> key   = " + config.browserstackKey());
        System.out.println(">>> app   = " + config.androidApp());

        MutableCapabilities caps;
        String hub;

        switch (host) {
            case "emulation" -> {
                caps = localCaps(config, false);
                hub = config.appiumUrl();
            }
            case "real" -> {
                caps = localCaps(config, true);
                hub = config.appiumUrl();
            }
            case "browserstack" -> {
                caps = browserstackCaps(config);
                hub = config.browserstackHubUrl();
            }
            default -> throw new IllegalArgumentException(
                    "Android deviceHost: emulation, real, browserstack. Got: " + host);
        }

        return new AndroidDriver(hubUrl(hub), caps);
    }

    // ===== Локальный запуск (эмулятор / реальное устройство) =====
    private static MutableCapabilities localCaps(TestConfig config, boolean realDevice) {
        MutableCapabilities caps = androidCaps();

        // APK (локальный путь)
        caps.setCapability("appium:app", localApk(config));
        caps.setCapability("appium:ignoreHiddenApiPolicyError", true);

        // UDID — обязателен для реального устройства
        String udid = config.udid();
        if (realDevice && (udid == null || udid.isBlank())) {
            throw new IllegalStateException("Set -Dudid= to the USB device (adb devices)");
        }
        if (udid != null && !udid.isBlank()) {
            caps.setCapability("appium:udid", udid);
        }

        // Версия Android и имя устройства
        if (config.platformVersion() != null && !config.platformVersion().isBlank()) {
            caps.setCapability("appium:platformVersion", config.platformVersion());
        }
        if (config.deviceName() != null && !config.deviceName().isBlank()) {
            caps.setCapability("appium:deviceName", config.deviceName());
        }

        return caps;
    }

    // ===== BrowserStack =====
    private static MutableCapabilities browserstackCaps(TestConfig config) {
        MutableCapabilities caps = androidCaps();

        caps.setCapability("appium:app", required("browserstack.app", config.androidApp()));
        caps.setCapability("appium:deviceName", config.androidDeviceName());
        caps.setCapability("appium:platformVersion", config.androidOsVersion());

        Map<String, Object> bstack = new HashMap<>();
        bstack.put("userName", required("browserstack.user", config.browserstackUser()));
        bstack.put("accessKey", required("browserstack.key", config.browserstackKey()));
        bstack.put("projectName", "Wikipedia mobile tests");
        bstack.put("buildName", "wikipedia-onboarding");
        bstack.put("sessionName", "android");
        bstack.put("debug", true);

        caps.setCapability("bstack:options", bstack);
        return caps;
    }

    // ===== Общие Android-капабилити =====
    private static MutableCapabilities androidCaps() {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "org.wikipedia.alpha");
        caps.setCapability("appium:appActivity", "org.wikipedia.main.MainActivity");
        caps.setCapability("appium:appWaitActivity", "*");
        caps.setCapability("appium:autoGrantPermissions", true);
        caps.setCapability("appium:noReset", false);
        caps.setCapability("appium:newCommandTimeout", 120);
        return caps;
    }

    // ===== Локальный APK =====
    private static String localApk(TestConfig config) {
        String configured = config.localApp();
        Path path = Path.of(configured);
        if (!path.isAbsolute()) {
            path = Path.of(System.getProperty("user.dir")).resolve(path);
        }
        path = path.toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new IllegalStateException(
                    "APK not found at " + path
                            + ". Скачай Wikipedia APK и положи в src/test/resources/apps/wikipedia.apk");
        }
        return path.toString();
    }

    // ===== Проверки =====
    private static String required(String key, String value) {
        if (value == null || value.isBlank() || value.startsWith("${")) {
            throw new IllegalStateException(
                    "Set " + key + " in config/browserstack.properties or -D" + key + "=");
        }
        return value;
    }

    private static java.net.URL hubUrl(String spec) {
        try {
            return URI.create(spec).toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalStateException("Invalid hub URL: " + spec, e);
        }
    }
}