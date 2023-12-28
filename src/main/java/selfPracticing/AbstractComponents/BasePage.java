package selfPracticing.AbstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selfPracticing.pages.OrdersPage;
import java.time.Duration;

public class BasePage
{
    WebDriver driver;
    WebDriverWait wait ;

    @FindBy(css="[routerlink*='myorders']")
    WebElement ordersBtn;

    public BasePage(WebDriver driver)
    {
        this.driver=driver;
    }

    public void waitForElementBySelectorToBeDisplayed(By elementSelector)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait until the element to be displayed
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementSelector)));
    }

    public void waitForElementToBeDisplayed(WebElement element)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait until the element to be displayed
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForPageToBeLoaded()
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait until the page is loaded
        wait.until(driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public void waitForElementToDisappear(By elementSelector)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait until the element to be displayed
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(elementSelector)));
    }

    public void clickOnElement(WebElement element)
    {
        waitForElementToBeClickable(element);
        element.click();
    }

    public void waitForElementToBeClickable(WebElement element)
    {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait until the element to be displayed
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public OrdersPage openOrdersPage()
    {
        clickOnElement(ordersBtn);
        return new OrdersPage(driver);
    }

}
