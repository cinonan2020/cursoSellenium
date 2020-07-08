package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import util.ConfigurationProperties;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static Driver instance             = null;
    private ThreadLocal<WebDriver> webDriver   = new ThreadLocal<WebDriver>();
    private static final String arquitecturaOS = System.getProperty("os.arch");

    public static Driver getInstance() {
        if ( instance == null ) {
            instance = new Driver();
        }
        return instance;
    }

    /**
     * @param navegador  es el nombre del navegador a instanciar
     * @param preferencias son las preferencias con las
     * @throws Exception
     */

    public final void setDriver(String navegador,
                                HashMap<String, String> preferencias, String... metodo)
            throws Exception {
        if(!Boolean.parseBoolean(ConfigurationProperties.sauceLabsMode)) {
            if (navegador.compareTo("firefox") == 0) {
                WebDriverManager.firefoxdriver().arch32();
                WebDriverManager.firefoxdriver().driverVersion("0.26.0");
                WebDriverManager.firefoxdriver().setup();
                Driver.getInstance().getThreadDriver().set(new FirefoxDriver());
            } else {
                if (navegador.compareTo("chrome") == 0) {
                    WebDriverManager.chromedriver().arch32();
                    WebDriverManager.chromedriver().setup();
                    if (Boolean.parseBoolean(ConfigurationProperties.headLessMode)) {
                        ChromeOptions chOptions = new ChromeOptions();
                        chOptions.setHeadless(true);
                        Driver.getInstance().getThreadDriver().set(new ChromeDriver(chOptions));
                    } else {
                        Driver.getInstance().getThreadDriver().set(new ChromeDriver());
                    }
                } else {
                    if (navegador.compareTo("IE") == 0) {
                        WebDriverManager.iedriver().arch32();
                        WebDriverManager.iedriver().driverVersion("3.141.59");
                        WebDriverManager.iedriver().setup();
                        Driver.getInstance().getThreadDriver().set(new InternetExplorerDriver());
                    } else {
                        if (navegador.compareTo("edge") == 0) {
                            WebDriverManager.edgedriver().setup();
                            Driver.getInstance().getThreadDriver().set(new EdgeDriver());
                        } else {
                            if (navegador.compareTo("opera") == 0) {
                                if (arquitecturaOS.contains("64")) {
                                    WebDriverManager.operadriver().arch64();
                                } else {
                                    WebDriverManager.operadriver().arch32();
                                }
                                WebDriverManager.operadriver().setup();
                                OperaOptions options = new OperaOptions();
                                options.setBinary("C:\\Users\\Usuario\\AppData\\Local\\Programs\\Opera\\65.0.3467.48\\opera.exe");
                                Driver.getInstance().getThreadDriver().set(new OperaDriver(options));
                            } else {
                                if (navegador.compareTo("safari") == 0) {
                                    Driver.getInstance().getThreadDriver().set(new SafariDriver());
                                }
                            }
                        }
                    }
                }
            }
        }else{
            DesiredCapabilities capabilities = new DesiredCapabilities();

            DesiredCapabilities caps = new DesiredCapabilities();
            capabilities.setCapability("os", preferencias.get("platformName"));
            capabilities.setCapability("os_version", preferencias.get("platformVersion"));
            capabilities.setCapability("browser", preferencias.get("browserName"));
            capabilities.setCapability("browser_version", preferencias.get("browserVersion"));
            if(metodo != null){
                capabilities.setCapability("name",metodo[0]);
            }else{
                capabilities.setCapability("name",preferencias.get("name"));
            }

            Driver.getInstance().getThreadDriver().set(new RemoteWebDriver(new URL(ConfigurationProperties.sauceLabsUrl),
                    capabilities));
        }
        Driver.getInstance().getDriver().manage().window().maximize();

    }


    /**
     * @return devuelve el driver del hilo correspondiente.
     */
    public WebDriver getDriver() {
        return webDriver.get();
    }
    public ThreadLocal<WebDriver>   getThreadDriver(){
        return webDriver;
    }


    /**
     *  método que sirve para cerrar las ventanas del navegador.
     */

    public void closeDriver() {
        if (getDriver() != null){
            getDriver().quit();
        }

    }





}