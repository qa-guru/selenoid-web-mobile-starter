package tests;

import com.codeborne.selenide.Configuration;
import drivers.SelenoidMobileDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class TestBase {

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("selenoid.url", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        System.setProperty("video.url", "https://selenoid.autotests.cloud/video/");
        String platform =  System.getProperty("platform");

        if (platform.equals("web")) {
//            Configuration.browser = "chrome";
//            Configuration.browserVersion = "89.0"; // 88.0 // 87.0

//            Configuration.browser = "opera";
//            Configuration.browserVersion = "75.0"; // 74.0 // 73.0
//
//            Configuration.browser = "firefox";
//            Configuration.browserVersion = "87.0"; // 86.0 // 85.0

//            Configuration.browser = "safari";
//            Configuration.browserVersion = "13.0";

            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability("browserName", "opera");
//            capabilities.setCapability("browserVersion", "73.0");

            if(!System.getProperty("selenoid.url").equals("")) {
                Configuration.remote = System.getProperty("selenoid.url");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", true);
            }
            Configuration.browserCapabilities = capabilities;


        } else if (platform.equals("selenoid_android")) {
            Configuration.browser = SelenoidMobileDriver.class.getName();
            Configuration.startMaximized = false;
            Configuration.browserSize = null;
            Configuration.timeout = 10000;
        }

        addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    public void afterEach() {
        String sessionId = getSessionId();

        attachScreenshot("Last screenshot");
        attachPageSource();
        if (System.getProperty("video.url") != "")
            attachVideo(sessionId);
        closeWebDriver();
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

}
