package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public  class PadrePage {

    protected WebDriver         driver;
    protected WebDriverWait      wait ;
    protected int           timeOutSeg;

    public PadrePage(WebDriver driver){
        this.driver = driver;
        this.timeOutSeg = 15;
        wait = new WebDriverWait(driver,timeOutSeg);
        PageFactory.initElements(driver,this);
    }

    /** Este método llama al submit del form
     * donde el elemento es el mismo form u otra elemento
     * web dentro del form
     * @param elemento
     */
    public void submit (WebElement elemento) {
        wait.until(ExpectedConditions.visibilityOf(elemento));
        elemento.submit();
    }

    public void clickElement(WebElement elemento){
        scrollIntoViewElement(elemento);
        wait.until(ExpectedConditions.elementToBeClickable(elemento));
        elemento.click();
    }
    public  void sendKeysElement(WebElement elemento, String cadena){
        scrollIntoViewElement(elemento);
        wait.until(ExpectedConditions.elementToBeClickable(elemento));
        elemento.clear();
        elemento.sendKeys(cadena);
    }
    public String returnTextElement(WebElement elemento){
        scrollIntoViewElement(elemento);
        return elemento.getText();
    }

    public void scrollIntoViewElement(WebElement elemento){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public void scrollIntoViewElementAndClickJS(WebElement elemento){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    wait.until(ExpectedConditions.visibilityOf(elemento));
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    wait.until(ExpectedConditions.elementToBeClickable(elemento));
                    js.executeScript("arguments[0].click();", elemento);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }
    public void scrollIntoViewElementAndSendKeys(WebElement elemento,String cadena){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    wait.until(ExpectedConditions.visibilityOf(elemento));
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    wait.until(ExpectedConditions.elementToBeClickable(elemento));
                    elemento.sendKeys(cadena);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }
    public void scrollIntoViewElementAndClick(WebElement elemento){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    wait.until(ExpectedConditions.visibilityOf(elemento));
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    wait.until(ExpectedConditions.elementToBeClickable(elemento));
                    elemento.click();
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public void esperarQueSeLleneSelect(Select select){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    if(select.getOptions().size()>0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public void scrollIntoViewElementClearAndSendKeys(WebElement elemento,String cadena){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    wait.until(ExpectedConditions.visibilityOf(elemento));
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    wait.until(ExpectedConditions.elementToBeClickable(elemento));
                    js.executeScript("arguments[0].value='"+cadena+"';", elemento);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public void esperarHastaQueTextoEsteVacio(WebElement elemento){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    if(elemento.getText().length()==0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public boolean esperarYDevolverTrueCuandoArchivoExista(File file){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        boolean existeFile = false;

        existeFile = (boolean) apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                    if(file.exists()){
                        return true;
                    }else{
                        return false;
                    }
            }
        });
        return existeFile;
    }

    public void seleccionarPaisXTextoVisible( WebElement selectWE,String texto){
        scrollIntoViewElement(selectWE);
        Select select= new Select(selectWE);
        esperarQueSeLleneSelect(select);
        select.selectByVisibleText(texto);
    }

    public void subirArchivo(WebElement elemento,String cadena){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    elemento.sendKeys(cadena);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public void clickJSMalaSintaxis(WebElement elemento) throws InterruptedException {
        new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(elemento));
        try{
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("arguments[0].click(;", elemento);
        }catch (org.openqa.selenium.JavascriptException e){
            e.printStackTrace();
        }
    }

    public void clickJSScriptTimeOut(WebElement elemento) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(elemento));
        try{
            driver.manage().timeouts().setScriptTimeout(2, TimeUnit.NANOSECONDS);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("arguments[0].click();", elemento);
        }catch (org.openqa.selenium.JavascriptException e){
            e.printStackTrace();
        }
    }

    public void scrollIntoViewElementAndClickTimeOut(WebElement elemento){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    wait.until(ExpectedConditions.visibilityOf(elemento));
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    wait.until(ExpectedConditions.elementToBeClickable(elemento));
                    elemento.click();
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        });
    }

    public boolean cargoElemento(WebElement elemento) {
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        boolean retorna = (boolean)apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    if (elemento.isDisplayed()) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });
        return retorna;
    }
    public String esperarTextoNoEsteVacioYDevolver(WebElement elemento){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    if(elemento.getText().length()!=0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });
        return elemento.getText();
    }

    public String esperarValueInputNoEsteVacioYDevolver(WebElement elemento){
        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView(true);", elemento);
                    if(elemento.getAttribute("value").length()!=0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });
        return elemento.getAttribute("value");
    }


    public String devolverTextSeleccionActualSelect(WebElement select){

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
               try{
                    Select selectQuery = new Select(select);
                    if(selectQuery.getOptions().size()>0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });
        Select selectQuery = new Select(select);
        return selectQuery.getFirstSelectedOption().getText();
    }




}
