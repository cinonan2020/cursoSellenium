package sauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

public class InventoryPage extends PadrePage {
    @FindBy(className = "product_label")
    WebElement myInventoryHeader;

    @FindBy(xpath = "//button[contains(text(),'Open Menu')]")
    WebElement openMenu;

    @FindBy(className ="bm-burger-button")
    WebElement burgerButton;

    @FindBy(id="logout_sidebar_link")
    WebElement logoutSidebarLink;


    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifificarSiCargoPagina(){
        if(cargoElemento(myInventoryHeader)){
            return true;
        }else{
            return false;
        }
    }

    public boolean verifificarSiCargoBurgerButton(){
        if(cargoElemento(burgerButton)){
            return true;
        }else{
            return false;
        }
    }
}
