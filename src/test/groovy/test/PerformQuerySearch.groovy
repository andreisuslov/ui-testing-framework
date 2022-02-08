package test



import io.qameta.allure.Description
import org.testng.annotations.Test
import pageobjects.GooglePage
import parcers.DP
import parcers.TestParameters


/**
 * This test will perform a search query to verify search results
 * @query -> text we would like to search via google search bar
 */
class PerformQuerySearch {

    private GooglePage googlePage = new GooglePage()

    @Test(dataProvider = "DP", dataProviderClass = DP)
    @TestParameters(['query'])
    @Description("Search query on google website")
    void performSearch(String query) {
        GooglePage.search query
    }
}

