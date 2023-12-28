package selfPracticing.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.testng.Assert.*;

public class StandAloneTest
{
    public static void main(String[] args)
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productToTest = "I PHONE";
        String TestingCountry="Germany";

        //Switch to the web URL
        driver.get("https://rahulshettyacademy.com/client");

        //Use the test credentials to login
        driver.findElement(By.id("userEmail")).sendKeys("test4321@test.com");
        driver.findElement(By.id("userPassword")).sendKeys("123456Test");
        driver.findElement(By.id("login")).click();

        //Inspect the whole items displayed in the home page
        List<WebElement> products = driver.findElements(By.cssSelector("div[class='card-body']"));

        //Search for the targeted product
        WebElement productToChoose = products.stream()
                .filter(product->product.findElement(By.tagName("b")).getText().contains(productToTest))
                .findFirst().orElse(null);

        //Click on Add to cart button for the found product
        productToChoose.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        //Wait until the toast message confirming adding to cart to be displayed
        WebDriverWait waitForToastMsg = new WebDriverWait(driver,Duration.ofSeconds(3));

        //wait until loading sign to disappear and the toast message to appears
        waitForToastMsg.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        waitForToastMsg.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        assertEquals(driver.findElement(By.cssSelector("#toast-container")).getText(),"Product Added To Cart");

       //Switch to the cart page
       WebElement cartBtn = driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']"));
       cartBtn.click();
       assertTrue(driver.getCurrentUrl().equals("https://rahulshettyacademy.com/client/dashboard/cart"));

       //Check the cart list matches the already selected product
        List <WebElement> productsList = driver.findElements(By.cssSelector("[class='cartSection'] h3"));
       Boolean itemExistInCart = productsList.stream().anyMatch(cartProduct->cartProduct.getText().contains(productToTest));
       assertTrue(itemExistInCart);

       //Go to Checkout page
       WebElement checkOutBtn = driver.findElement(By.xpath("//button[text()='Checkout']"));
       checkOutBtn.click();
       assertTrue(driver.getCurrentUrl().contains("https://rahulshettyacademy.com/client/dashboard/order?"));

       //Fill the country in checkout page
         //1- Find the country textbox
        WebElement countryTxtBox = driver.findElement(By.cssSelector("input[placeholder='Select Country']"));

        //2- Choose the targeted testing country from the suggested list
        Actions action = new Actions(driver);
        action.sendKeys(countryTxtBox , TestingCountry).build().perform();
        driver.findElement(By.cssSelector("span.ng-star-inserted:first-child")).click();

        // 3- Click on Submit button
        WebElement placeOrderBtn = driver.findElement(By.cssSelector(".action__submit"));
        placeOrderBtn.click();

        //4- Verify the thank-you message

        WebElement thankYouMsg = driver.findElement(By.cssSelector(".hero-primary"));
        assertEquals(thankYouMsg.getText(),"THANKYOU FOR THE ORDER.");

        //5- Verify Toast Msg
        waitForToastMsg.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-title")));
        WebElement toastConfirmationMsg = driver.findElement(By.cssSelector(".toast-title"));
        assertEquals(toastConfirmationMsg.getText(),"Order Placed Successfully");

        driver.quit();
    }
}
