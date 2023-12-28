package selfPracticing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;

import java.util.List;

public class CartPage extends BasePage
{
    WebDriver driver;
    @FindBy(css= ("[class='cartSection'] h3"))
    List<WebElement> productList;
    @FindBy(xpath = ("//button[text()='Checkout']"))
    WebElement checkOutBtn;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public Boolean verifyProductDisplayInCartPage(String productName)
    {
      // waitForPageToBeLoaded();
       //productList.forEach(prod -> {System.out.print("Products " + prod.getText() + " ");});
       return (productList.stream().anyMatch(cartProduct->cartProduct.getText().contains(productName)));
    }

    public Boolean verifyWrongProductDisplayInCartPage(String productName)
    {
        waitForPageToBeLoaded();
        return (productList.stream().anyMatch(cartProduct->cartProduct.getText().contains(productName+"xx")));
    }

    public CheckOutPage clickOnCheckoutBtn()
    {
        checkOutBtn.click();
        return new CheckOutPage(driver);
    }
}
