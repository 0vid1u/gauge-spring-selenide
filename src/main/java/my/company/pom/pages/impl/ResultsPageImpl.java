package my.company.pom.pages.impl;

import my.company.pom.pages.ResultsPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

/**
 * @author otuvrila
 */

public class ResultsPageImpl implements ResultsPage {
    @Override
    public int getResultsCount() {
        return $$(By.xpath("//div[@class='g']")).size();
    }
}
