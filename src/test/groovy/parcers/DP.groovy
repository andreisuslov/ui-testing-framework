package parcers

import org.testng.ITestContext
import org.testng.annotations.DataProvider
import utilities.ConfigReader

import java.lang.reflect.Method

class DP {
    private DP() {
        //not called
    }
    @DataProvider(name = "DP")
    public static Object[][] getDataFromFile(final Method testMethod, ITestContext context) {
        TestParameters parameters = testMethod.getAnnotation(TestParameters.class)
        String[] fields = parameters.value()
        String dataFileName = context.getCurrentXmlTest().getParameter("dataParamFile");
        String dataFileDir = ConfigReader.getProperty("suite")
        String dataFilePath = dataFileDir + "/" + dataFileName
        println("Loading the data parameters file: " + dataFilePath)
        File tFile = new File(dataFilePath)

        if(dataFileName.endsWith(".yaml"))
            return YamlDataParcer.parseFile(fields,tFile)
        else
            return JsonDataParcer.parseFile(fields,tFile)
    }
}