package sauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

public class MenuBar extends PadrePage {

    @FindBy(id = "logout_sidebar_link")
    WebElement logout;

    @FindBy(xpath = "//button[contains(text(),'Open Menu')]")
    WebElement openMenu;

    public MenuBar(WebDriver driver) {
        super(driver);
    }


    public void clickMenuOpciones(){
        openMenu.click();
    }
    public void seleccionarClickOpcionLogout(){
        scrollIntoViewElementAndClick(logout);
    }

}