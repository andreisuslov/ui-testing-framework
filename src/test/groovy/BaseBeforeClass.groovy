import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.ex.ElementNotFound
import groovy.json.JsonSlurper
import org.json.JSONObject

import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogType
import org.testng.ITestContext
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.Listeners
import org.testng.annotations.BeforeClass

import static com.codeborne.selenide.Selenide.*
import static helper.GeneralHelper.*
import static helper.PerformanceTimer.*
import static java.time.Duration.*
import static utilities.ConfigReader.getPropertyValue

@Listeners([TestListener.class])
abstract class BaseBeforeClass {

    public String userPrefix
    public String testName

    @BeforeClass
    void start(ITestContext context) {
        Configuration.reportsFolder = "test-result/reports";
        WebDriverRunner.getWebDriver().manage().timeouts().scriptTimeout(ofSeconds(10))
        userPrefix = context.getCurrentXmlTest().getParameter("userPrefix")
        userPrefix = userPrefix ?: ""
        testName = userPrefix + context.getCurrentXmlTest().getParameter("dataParamFile")
        Configuration.baseUrl = getPropertyValue("url.baseUrl")
    }

    static boolean checkForLoginErrors() {
        String errorXpath = "//p[contains(@class,'login-error')]"
        def errorMessage
        if ($$x(errorXpath).size() > 0) {
            try {
                errorMessage = $x(errorXpath).getText()
            } catch (ElementNotFound e) {
            }
            if (!errorMessage.isEmpty()) {
                def message = "Error During Login: $errorMessage"
                printInTest(message)
            }
            return true
        }
        return false
    }

    @AfterClass(alwaysRun = true)
    void finalizeCommon() {
        logExecutionTime("End test")
        analyzeBrowserLog()
        analyzePerfLog()
        checkForLoginErrors()
        WebDriverRunner.getWebDriver().quit()
    }


    static boolean waitForViewToLoad(int timeToWaite) {
        def currentTime = System.currentTimeMillis()
        def maxTime = currentTime + timeToWaite
        Boolean condition = false
        while (currentTime < maxTime && !condition) {
            condition = true // there's supposed by be a condition of a visibility of an element
            currentTime = System.currentTimeMillis()
        }
        return !condition
    }

    void analyzeBrowserLog() {
        LogEntries logEntries = WebDriverRunner.getWebDriver().manage().logs().get(LogType.BROWSER)
        File chromeConsoleLog = new File(System.getProperty("user.dir") + "\\test-output\\" + "\\browserLogs\\" + testName + "_colsol.txt")
        File logDir = new File(chromeConsoleLog.getParent())
        if (!logDir.exists()) {
            logDir.mkdirs()
            chromeConsoleLog.createNewFile()
        }
        chromeConsoleLog.withWriter { out ->
            logEntries.each {
                if (!it.message.contains("\"level\":\"info\"")) {
                    out.println it
                }
            }
        }
    }

    public void analyzePerfLog() {
        File chromePerfLog = new File(System.getProperty("user.dir") + "\\test-output\\" + "\\browserLogs\\" + testName + "_perform.txt")
        File logDir = new File(chromePerfLog.getParent())
        if (!logDir.exists()) {
            logDir.mkdirs()
            chromePerfLog.createNewFile()
        }
        LogEntries logEntries = WebDriverRunner.getWebDriver().manage().logs().get(LogType.PERFORMANCE)
        def jsonSlurper = new JsonSlurper()
        chromePerfLog.withWriter { out ->
            out.print('[')
            logEntries.each {
                def obj = jsonSlurper.parseText(it.getMessage())
                JSONObject string = obj.message.params
                out.println(',')
                out.print(string)
            }
            out.print(']')
        }
    }


    @AfterMethod(alwaysRun = true)
    void closeMainApplication() {
        switchTo().defaultContent()
        println "After method is finished"
    }
}
