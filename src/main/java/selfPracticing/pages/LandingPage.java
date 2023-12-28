package selfPracticing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;

public class LandingPage extends BasePage {

    WebDriver driver;

    public LandingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="userEmail")
    WebElement userEmail;

    @FindBy(id="userPassword")
    WebElement userPassword;

    @FindBy(id="login")
    WebElement loginBtn;

    @FindBy(css=".toast-message")
    WebElement loginErrorMsg;


    public void gotToLandingPage()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }
    public ProductCatalogue loginApplication(String email , String password) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        loginBtn.click();
        return new ProductCatalogue(driver);
    }

    public String getLoginErrorMsg()
    {
        waitForElementToBeDisplayed(loginErrorMsg);
        return loginErrorMsg.getText();
    }


}
