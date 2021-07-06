package tests;

import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.MobileBy.AccessibilityId;
import static io.qameta.allure.Allure.step;

public class WikipediaTests extends TestBase {

    @Test
    @Tag("web")
    @DisplayName("Successful search for 'Docker' in wikipedia.org")
    void searchWebTest() {
        open("https://ru.wikipedia.org/w/index.php?search=");
        step("Type search", () -> {
            $(byName("search")).setValue("docker")
                    .pressEnter();
        });
        step("Verify content found", () ->
                $$(".mw-search-result")
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @Tag("selenoid_android")
    @DisplayName("Successful search for 'Docker' in wikipedia android app")
    void searchAndroidTest() {
        open(); // selenide specific
        back(); // close onboarding screen
        step("Type search", () -> {
            $(AccessibilityId("Search Wikipedia")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).setValue("docker");
        });
        step("Verify content found", () ->
                $$(MobileBy.id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }
}
