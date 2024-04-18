package parcers

import org.testng.ITestContext
import org.testng.annotations.DataProvider
import utilities.ConfigReader

import java.lang.reflect.Method

// Provides data for TestNG tests from YAML or JSON files
class DP {

    // Private constructor to prevent instantiation
    private DP() {}

    /**
     * DataProvider that reads data from a YAML or JSON file based on the test method annotation and test context.
     * @param testMethod The test method being executed.
     * @param context The TestNG test context.
     * @return A 2D array of data objects.
     */
    @DataProvider(name = "DP")
    static Object[][] getDataFromFile(Method testMethod, ITestContext context) {
        TestParameters parameters = testMethod.getAnnotation(TestParameters.class)
        String[] fields = parameters.value()
        String dataFileName = context.getCurrentXmlTest().getParameter("inputDataFile")
        String dataFileDir = ConfigReader.getPropertyValue("suite")
        String dataFilePath = "$dataFileDir/suites/smoke/$dataFileName"

        println "Loading the data parameters file: $dataFilePath"
        File tFile = new File(dataFilePath)
        println "Full path to the file: ${tFile.absolutePath}"

        if (dataFileName.endsWith(".yaml")) {
            return YamlDataParcer.parseFile(fields, tFile)
        } else {
            return JsonDataParcer.parseFile(fields, tFile)
        }
    }
}