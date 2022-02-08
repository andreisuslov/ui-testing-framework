package pageobjects

import com.codeborne.selenide.SelenideElement
import helper.PerformanceTimer
import io.qameta.allure.Step

import static com.codeborne.selenide.Selenide.*
import static org.openqa.selenium.By.name


class GooglePage {

    public openGooglePage() {
        open("https://www.google.com/")
    }

    private SelenideElement getSearchBar() {
        $(name('q'))
    }

    @Step("Perform search")
    public void search(String query) {
        PerformanceTimer.logExecutionTime("Perform search") {
        searchBar.sendKeys(query)
        }
    }
}
