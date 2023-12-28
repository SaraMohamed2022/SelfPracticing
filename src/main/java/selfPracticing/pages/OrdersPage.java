package selfPracticing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selfPracticing.AbstractComponents.BasePage;
import java.util.List;

public class OrdersPage extends BasePage {
    WebDriver driver;

    @FindBy(css="tr th:nth-child(1)")
    List<WebElement> ordersIdList;
    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public Boolean verifyOrderIDdisplayed(String orderID) {
        return
                getOrdersIdList().stream()
                .anyMatch(order->order
                        .getText().contains(orderID));
    }

    public List<WebElement> getOrdersIdList()
    {
        waitForPageToBeLoaded();
        return ordersIdList;
    }

}
