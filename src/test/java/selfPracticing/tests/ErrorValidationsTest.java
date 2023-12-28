package selfPracticing.tests;

import org.testng.annotations.Test;
import selfPracticing.TestComponents.BaseTest;
import selfPracticing.TestComponents.RetryTestExecution;
import selfPracticing.pages.CartPage;
import selfPracticing.pages.ProductCatalogue;
import static org.testng.Assert.*;

public class ErrorValidationsTest extends BaseTest {


   // @Test(groups = {"ErrorHandling"})
   // @Test (retryAnalyzer = RetryTestExecution.class)
    @Test (groups = {"ErrorHandling"}, retryAnalyzer = RetryTestExecution.class)
    public void testNegativeLogin()
    {
        landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("invalidPassword"));
        assertEquals(landingPage.getLoginErrorMsg(),"Incorrect email or password.");
    }

    @Test
    public void negativeTestForProductAddedToCart()
    {
        String productToTest = "ZARA COAT 3";
        System.out.println(property.getProperty("validUserName") + "Property");
        ProductCatalogue productCatalogue = landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );

        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(productToTest);

        CartPage cartPage= productCatalogue.openCartPage();
        assertFalse(cartPage.verifyWrongProductDisplayInCartPage(productToTest));
    }



}
