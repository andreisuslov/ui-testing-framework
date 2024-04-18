package utilities;

class ConfigReader {
    //this class will read from config.properties file
    //then it will pass those values to our tests

    private static Properties properties = new Properties()
    //we need to load our configuration.properties file into properties variable

    static {
        try {
            // Specify the path to the properties file
            String path = "src/test/resources/config.properties"
            File propertiesFile = new File(path)

            // Load the properties from the file
            propertiesFile.withInputStream { inputStream ->
                properties.load(inputStream)
            }
        } catch (IOException e) { // Catch IOExceptions and print the stack trace for debugging
            e.printStackTrace()
        }
    }

    static String getPropertyValue(String key) {
        // Get the property by key, trim it to remove any leading or trailing spaces
        return properties.getProperty(key).trim()
    }
}