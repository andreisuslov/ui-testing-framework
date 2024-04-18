package pageobjects

import com.codeborne.selenide.SelenideElement
import helper.PerformanceTimer
import io.qameta.allure.Step

import static com.codeborne.selenide.Selenide.*
import static org.openqa.selenium.By.name
import static utilities.ConfigReader.getPropertyValue

class GooglePage {
    GooglePage open() {
        def url = getPropertyValue("url.baseUrl")
        PerformanceTimer.logExecutionTime("Open Google", {
            open(url)

        })
        return this
    }

    private SelenideElement getSearchBar() {
        $(name('q'))
    }

    @Step("Perform search for {query}")
    GooglePage search(String query) {
        PerformanceTimer.logExecutionTime("Perform search") {
            searchBar.sendKeys(query)
            searchBar.pressEnter()
        }
        return this
    }
}
