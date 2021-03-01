package my.company.steps.ui;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import my.company.pom.pages.ResultsPage;
import my.company.pom.pages.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@Component
public class GoogleSearchSteps {

    @Autowired
    private SearchPage searchPage;

    @Qualifier("resultsPageImpl")
    @Autowired
    private ResultsPage resultsPage;

    @Step("Open <url>")
    public void openUrl(String url) {
        open(url);
    }

    @Step("Search for <expression>")
    public void searchFor(String expression) {
        assertThat(searchPage.isAt()).isTrue();
        searchPage.searchFor(expression);
    }

    @Step("Ensure <expectedResultsCount> search results are shown")
    public void ensureResultsAreShown(int expectedResultsCount) {
        assertThat(resultsPage.isAt()).isTrue();
        assertThat(resultsPage.getResultsCount()).isEqualTo(expectedResultsCount);
    }
}
