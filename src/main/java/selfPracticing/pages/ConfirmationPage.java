package selfPracticing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;

import java.util.Arrays;
import java.util.List;

public class ConfirmationPage extends BasePage {
    WebDriver driver;
    public ConfirmationPage(WebDriver driver)
        {
            super(driver);
            this.driver = driver;
            PageFactory.initElements(driver,this);
        }
@FindBy(css=(".hero-primary") )
    WebElement thankYouMsg;
@FindBy(css=(".toast-title")  )
WebElement toastMsg;

@FindBy(css = "label.ng-star-inserted")
WebElement orderId;
public String getThankYouMsg()
{
    return thankYouMsg.getText();
}

public String getToastMsg()
{
    return toastMsg.getText();
}

    public String getOrderId()
    {
       return orderId.getText().replace("|","").trim();
    }
}
