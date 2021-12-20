package my.company.pom.pages.impl;

import com.codeborne.selenide.SelenideElement;
import my.company.pom.pages.SearchPage;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author otuvrila
 */

@Component
public class SearchPageImpl extends BasePage implements SearchPage {

    private final SelenideElement searchInput = $(By.name("q")).as("Search input");

    @Override
    public boolean isAt() {
        return $(searchInput).exists();
    }

    @Override
    public void searchFor(String expression) {
        setValue(searchInput, expression);
        searchInput.pressEnter();
    }
}
