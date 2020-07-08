package Examen;

import driver.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import sauceDemo.IndexPage;
import sauceDemo.InventoryPage;
import sauceDemo.MenuBar;
import util.Excel;
import util.reporter.GetScreenShot;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static util.reporter.MensajesReporte.cargarMensajeResultadoTestNOk;
import static util.reporter.MensajesReporte.cargarMensajeResultadoTestOk;

public class ExamenFinalTest {

    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    String excelPath = System.getProperty("user.dir")+"\\report\\excelOutput\\";
    @Factory(dataProvider = "dpBrowsers")
    public ExamenFinalTest(String browserName) {
        preferencias.put("browserName", browserName);
    }
    public ExamenFinalTest(){
    }

    @DataProvider
    public Object[][] dpBrowsers() throws Exception {
        //return new Object[][] {{"chrome"},{"firefox"},{"IE"}};
        //return new Object[][] {{"IE"}};
        //return new Object[][] {{"firefox"}};
        return new Object[][]{{"chrome"}};
    }

    @BeforeMethod
    public void setUp(Method method) throws Exception {
        Driver.getInstance().setDriver(preferencias.get("browserName"), preferencias);
        url_AUT = "https://www.saucedemo.com/index.html";
        Driver.getInstance().getDriver().get(url_AUT);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }

    @Test (dataProvider = "dp_cp001")
    public void cp001_loginNoExitoso(HashMap<Object,Object> inputData){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        IndexPage indexPage = new IndexPage(driver);

        indexPage.ingresarCredenciales(inputData);
        String mensajeErrorActual   = indexPage.devolverMensajeError();
        String mensajeErrorEsperado = inputData.get("mensajeError").toString();

        if(mensajeErrorActual.compareTo(mensajeErrorEsperado)==0){
            cargarMensajeResultadoTestOk(testResult,"Mensaje se muestra correctamente: "+mensajeErrorActual);
        }else{
            cargarMensajeResultadoTestNOk(testResult,
                    "Mensaje esperado : "+mensajeErrorEsperado+"<br>" +
                            "Mensaje actual  : "+mensajeErrorActual
            );
        }
    }

    @Test (dataProvider = "dp_cp002")
    public void cp002_loginExitoso(HashMap<Object,Object> inputData) throws IOException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();

        IndexPage indexPage = new IndexPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        indexPage.ingresarCredenciales(inputData);
        boolean cargo = inventoryPage.verifificarSiCargoPagina();


        GetScreenShot.capturarPantallaBase64(driver);
        GetScreenShot.capturarPantallaFile(driver, testResult.getMethod().getMethodName()+".png");

        List<String> mensajeReporte = new ArrayList<>();
        if(cargo){
            String mensajeOk = "Cargó correctamente My Inventory page.";
            mensajeReporte.add(mensajeOk);
            cargarMensajeResultadoTestOk(testResult,mensajeOk);
            Excel.crearExcel(excelPath+testResult.getMethod().getMethodName()+".xlsx",mensajeReporte);

        }else{
            String mensajeNOk = "No cargó correctamente My Inventory page.";
            cargarMensajeResultadoTestNOk(testResult,mensajeNOk);
            Excel.crearExcel(excelPath+testResult.getMethod().getMethodName()+".xlsx",mensajeReporte);
        }
    }

    @Test (dataProvider = "dp_cp003")
    public void cp003_deslogeoExitoso(HashMap<Object,Object> inputData) throws IOException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();

        IndexPage indexPage           = new IndexPage(driver);
        InventoryPage inventoryPage     = new InventoryPage(driver);
        MenuBar menuBar     = new MenuBar(driver);

        indexPage.ingresarCredenciales(inputData);
        boolean cargoInventoryPage = inventoryPage.verifificarSiCargoBurgerButton();
        if(cargoInventoryPage) {
            menuBar.clickMenuOpciones();
            menuBar.seleccionarClickOpcionLogout();
        }
        boolean cargoLoginPage = indexPage.verifificarSiCargoPagina();

        GetScreenShot.capturarPantallaBase64(driver);
        GetScreenShot.capturarPantallaFile(driver, testResult.getMethod().getMethodName()+".png");

        List<String> mensajeReporte = new ArrayList<>();
        if (cargoLoginPage) {
            String mensajeOk = "Cargó correctamente My Login page.";
            mensajeReporte.add(mensajeOk);
            cargarMensajeResultadoTestOk(testResult, mensajeOk);
            Excel.crearExcel(excelPath+testResult.getMethod().getMethodName()+".xlsx",mensajeReporte);
        }
    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp001(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp001_Ex.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp002(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp002_Ex.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp003(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp003_Ex.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }

    @AfterMethod
    public void tearDown(){
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }

}
