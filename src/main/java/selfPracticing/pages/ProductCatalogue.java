package selfPracticing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;

import java.util.List;

public class ProductCatalogue extends BasePage {

    WebDriver driver;
    By toastMsg = By.cssSelector(".ng-animating");
    By product = By.cssSelector("div[class='card-body']");
    By loadingSign = By.cssSelector(".ng-animating");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    @FindBy(css ="div[class='card-body']")
    List<WebElement> productsList;

    @FindBy(css = "button[routerlink='/dashboard/cart']")
    WebElement cartBtn;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }



    public List<WebElement> getProductsList() {
        waitForElementBySelectorToBeDisplayed(product);
        return productsList;
    }

    public WebElement getProductByName(String productName)
    {
      return getProductsList().stream()
                .filter(product->product
                        .findElement(By.tagName("b")).getText().contains(productName))
                .findFirst().orElse(null);
    }

    public void addProductToCart(String productName)
    {
       WebElement testingProduct = getProductByName(productName);
       testingProduct.findElement(addToCart).click();
       waitForElementBySelectorToBeDisplayed(toastMsg);
       waitForElementToDisappear(loadingSign);
    }

    public CartPage openCartPage()
    {
        waitForPageToBeLoaded();
        waitForElementToDisappear(loadingSign);
        clickOnElement(cartBtn);
        return new CartPage(driver);
    }

}
