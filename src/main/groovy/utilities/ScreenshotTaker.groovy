package utilities

import io.qameta.allure.Attachment
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;


/**
 * Stands for taking screenshots through Selenium API and storing them into the file system.
 *
 *
 */
public class ScreenshotTaker {

    private TakesScreenshot driver
    private File outputDir

    ScreenshotTaker(WebDriver driver, File outputDir ) {
        this.driver = driver as TakesScreenshot
        this.outputDir = outputDir
    }

    /**
     * Takes a screenshot with the given key storing it into the given output directory.
     *
     * @param key the screenshot parameter representing a context where it's taken
     * @return the newly created screenshot, or 'null' if the screenshot is not taken for whatever reason
     */
    @Attachment(value = "Screenshot_{key}", type = "image/png")
    byte[] takeScreenshot(String key) {
        String base64Screenshot
        try {
            base64Screenshot = driver.getScreenshotAs(OutputType.BASE64)

        } catch (WebDriverException e) {
            println "Couldn't take a screenshot, see exception below"
            e.printStackTrace()
            return null // silently returning 'null'
        }
        outputDir.mkdirs() // create dir(s) if it doesn't exist
        String imageFileName = "${key}_${System.currentTimeMillis()}.png"
        byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes())
        File imageFile = new File(outputDir, imageFileName)
        imageFile.withOutputStream { fos ->
            fos.write(decodedScreenshot)
        }
        println "stored OS screenshot for '${key}' at '${imageFile.getAbsolutePath()}'"
        decodedScreenshot
    }

}


