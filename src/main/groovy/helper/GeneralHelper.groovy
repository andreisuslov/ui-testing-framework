package helper

import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.ex.ElementNotFound
import org.openqa.selenium.StaleElementReferenceException

import static com.codeborne.selenide.Condition.*
import static com.codeborne.selenide.Selenide.*
import static java.time.Duration.ofSeconds
import static org.openqa.selenium.Keys.*
import static com.codeborne.selenide.WebDriverRunner.getWebDriver

class GeneralHelper {

    public static void doWithRetries(int maxAttempts = 5, Closure code) {
        int attempts = 0
        while (attempts < maxAttempts) {
            try {
                code.call()
                break
            } catch (StaleElementReferenceException ignored) {
                println("Caught StaleElementReferenceException: stale element reference. Retrying...")
            } catch (ElementNotFound e) {
                println("Caught ElementNotFound exception. Retrying...")
            }
            attempts++
            sleep(1000)
        }
        if (attempts >= maxAttempts)
            throw new StaleElementReferenceException(println("Max amount of retries reached: $maxAttempts. StaleElementReferenceException still occurs."))
    }

    public static customWaitFor(int timeOut, String message, Closure code) {
        def currentTime = System.currentTimeMillis()
        def maxTime = currentTime + timeOut
        Boolean condition = false
        while (currentTime < maxTime && !condition) {
            condition = code.call()
            currentTime = System.currentTimeMillis()
        }
        assert condition, "$message. TimeOut reached: $timeOut."
    }

    public static void highlightElement(SelenideElement element) {
        String style = "border: 2px dashed purple"
        executeJavaScript("arguments[0].setAttribute('style', arguments[1]);", element, style)
        Thread.sleep(800)
        executeJavaScript("arguments[0].setAttribute('style','')", element)
    }

    public static void contextClick(SelenideElement element) {
        element.should(exist, ofSeconds(10)).shouldBe(visible, ofSeconds(10))
        highlightElement(element);
        actions().contextClick(element).build().perform();
    }

    public static void pressEnter() {
        webDriver.switchTo().activeElement().sendKeys(ENTER)
    }

    static void ctrlClick(SelenideElement el) {
        actions().keyDown(CONTROL)
                .click(el)
                .keyUp(CONTROL)
                .perform()
    }

    static void pressArrowDown() {
        webDriver.switchTo().activeElement().sendKeys(ARROW_DOWN)
    }
}
