package pageobjects

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.ElementsCollection

import static com.codeborne.selenide.CollectionCondition.itemWithText

import static com.codeborne.selenide.Selenide.$$x

class SearchResults {
    private final ElementsCollection elements = $$x("//h3[contains(@class, 'LC20lb')]")

    SearchResults shouldHaveSize(int size) {
        elements.shouldHave(CollectionCondition.size(size))
        return this
    }

    SearchResults shouldHaveResult(String text) {
        elements.shouldHave(itemWithText(text))
        return this
    }
}