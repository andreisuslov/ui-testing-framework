package test



import io.qameta.allure.Description
import org.testng.annotations.Test
import pageobjects.GooglePage
import pageobjects.SearchResults
import parcers.DataProvider
import parcers.TestParameters


/**
 * This test will perform a search query to verify search results
 * @query -> text we would like to search via google search bar
 */
class PerformQuerySearchTest {

    def googlePage = new GooglePage()
    def searchResults = new SearchResults()

    @Test(dataProvider = "DP", dataProviderClass = DataProvider)
    @TestParameters(['query', 'resultTitle'])
    @Description("Search query on google website")
    void performSearch(String query, String resultTitle) {
        GooglePage google = new GooglePage()
        SearchResults results = new SearchResults()
        println("Searching for: $query")
        google.open()
        google.search(query)
        results.shouldHaveResult(resultTitle);
    }
}

