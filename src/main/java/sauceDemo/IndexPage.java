package sauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;
import java.util.HashMap;

public class IndexPage extends PadrePage {

    @FindBy(id="user-name")
    WebElement usernameTxt;

    @FindBy(id="password")
    WebElement passwordTxt;

    @FindBy(className = "btn_action")
    WebElement btnLogin;

    @FindBy(tagName = "h3")
    WebElement mensajeError;

    @FindBy(className = "login_logo")
    WebElement loginLogo;

    public IndexPage(WebDriver driver) {
        super(driver);
    }

    public void ingresarCredenciales(HashMap<Object,Object> inputData){
        scrollIntoViewElementAndSendKeys(usernameTxt   ,inputData.get("username").toString());
        scrollIntoViewElementAndSendKeys(passwordTxt,inputData.get("password").toString());
        scrollIntoViewElementAndClick(btnLogin);
    }

    public String devolverMensajeError(){
        scrollIntoViewElement(mensajeError);
        return esperarTextoNoEsteVacioYDevolver(mensajeError);
    }

    public boolean verifificarSiCargoPagina(){
        if(cargoElemento(loginLogo)){
            return true;
        }else{
            return false;
        }
    }
}
