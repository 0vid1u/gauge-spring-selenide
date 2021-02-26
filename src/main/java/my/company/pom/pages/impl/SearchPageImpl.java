package my.company.pom.pages.impl;

import my.company.pom.pages.SearchPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author otuvrila
 */

public class SearchPageImpl implements SearchPage {

    private final By searchInput = By.name("q");

    @Override
    public void searchFor(String expression) {
        $(searchInput).setValue(expression).pressEnter();
    }
}
