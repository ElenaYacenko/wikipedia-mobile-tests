package wikipedia.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config/${env}.properties",
        "classpath:config/secret.properties",
        "classpath:config/default.properties"
})
public interface TestConfig extends Config {

    // ===== Appium Server =====
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String appiumUrl();

    // ===== Local Android =====
    @DefaultValue("Android")
    String platformName();

    @DefaultValue("")
    String platformVersion();

    @DefaultValue("")
    String deviceName();

    @DefaultValue("")
    String udid();

    @DefaultValue("UiAutomator2")
    String automationName();

    @DefaultValue("org.wikipedia.alpha")
    String appPackage();

    @DefaultValue("org.wikipedia.main.MainActivity")
    String appActivity();

    @DefaultValue("true")
    boolean noReset();

    // ===== BrowserStack =====
    @Key("browserstack.user")
    String browserstackUser();

    @Key("browserstack.key")
    String browserstackKey();

    @Key("browserstack.hubUrl")
    @DefaultValue("https://hub.browserstack.com/wd/hub")
    String browserstackHubUrl();

    @Key("android.deviceName")
    @DefaultValue("Google Pixel 7")
    String androidDeviceName();

    @Key("android.osVersion")
    @DefaultValue("13.0")
    String androidOsVersion();

    @Key("android.app")
    @DefaultValue("")
    String androidApp();

    // ===== Local APK =====
    @Key("local.app.name")
    @DefaultValue("app-alpha-universal-release.apk")
    String localAppName();

    @Key("local.app.baseUrl")
    @DefaultValue("https://github.com/wikimedia/apps-android-wikipedia/releases/download/latest/")
    String localAppBaseUrl();
}