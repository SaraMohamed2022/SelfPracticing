package selfPracticing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;

public class CheckOutPage extends BasePage
{
    WebDriver driver;
    Actions action;
    public CheckOutPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }



@FindBy(css = ("input[placeholder='Select Country']"))
WebElement countryTxtBox;

@FindBy(css = (".action__submit") )
WebElement submitBtn;


By testingCountry = By.cssSelector ("span.ng-star-inserted:first-child");

public void chooseCountry(String countryName)
{
    action = new Actions(driver);
    action.sendKeys(countryTxtBox , countryName).build().perform();
    driver.findElement(testingCountry).click();
}

public ConfirmationPage clickOnSubmit()
{
    submitBtn.click();
    return new ConfirmationPage(driver);
}
}
