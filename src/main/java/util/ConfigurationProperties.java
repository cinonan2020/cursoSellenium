package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProperties {

    public static String headLessMode  ;
    public static String sauceLabsMode ;
    public static String sauceLabsUrl ;
    public static String userNameSauce;
    public static String accesKeySauce;

    private static Properties testProperties = new Properties();
    private static InputStream input;

    public static void getConfigurationProperties() {
        try {


            // carga de propiedades generales del proyecto
            input = new FileInputStream("src/main/resources/properties/config.properties");
            testProperties.load(input);

            sauceLabsMode  = testProperties.getProperty("sauceLabsMode");
            headLessMode   = testProperties.getProperty("headLessMode");
            sauceLabsUrl        = "https://"+testProperties.getProperty("userNameSauce")+":"+
                    testProperties.getProperty("accesKeySauce")+"@hub-cloud.browserstack.com/wd/hub";
            accesKeySauce = testProperties.getProperty("accesKeySauce");
            userNameSauce =testProperties.getProperty("userNameSauce") ;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
