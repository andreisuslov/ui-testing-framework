package utilities;

public class ConfigReader {
    //this class will read from configuration.properties file
    //then it will pass those values to our tests

    private static Properties properties;
    //we need to load our configuration.properties file into properties variable

    static {

        try {
            //first of all, we need to provide a path to out properties file
            String path = "src/test/resources/configuration.properties";

            //to read from the file
            File propertiesFile = new File(path);

            //now we need to load into the properties variable
            propertiesFile.withInputStream {
                properties.load(it)
            }

        }catch(IOException e){ //in case this exception happens we want to where it is coming from and the reason for it
            e.printStackTrace();
        }

    }

    public static String getProperty(String key){
        return properties.getProperty(key).trim();
    }

}
