import com.codeborne.selenide.Selenide
import org.testng.ITestResult
import org.testng.TestListenerAdapter


class TestListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult testResult) {
          println("\nTest Class: ${testResult.getInstanceName()}")
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        println("--- Test failed! ---")
        String screenshotName = testResult.getInstance().class.simpleName + "_failShot"
        Selenide.screenshot(screenshotName)
        println("Stack trace: ${testResult.getThrowable().getMessage()}")
        super.onTestFailure(testResult)
    }


    @Override
    public void onTestSuccess(ITestResult testResult) {
        println("+++ Test passed +++")
        super.onTestSuccess(testResult)
    }
}