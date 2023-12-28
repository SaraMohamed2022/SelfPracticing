package selfPracticing.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selfPracticing.TestComponents.BaseTest;
import selfPracticing.pages.*;
import java.io.IOException;
import java.util.HashMap;
import static org.testng.Assert.*;

public class SubmitOrderTest extends BaseTest {
    String orderID;
    @Test
    public void submitOrder() throws IOException
    {
        String productToTest = "ZARA COAT 3";
        String testingCountry="Germany";
        ProductCatalogue productCatalogue = landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );

        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(productToTest);

        CartPage cartPage= productCatalogue.openCartPage();
        assertTrue(cartPage.verifyProductDisplayInCartPage(productToTest));
        assertTrue(driver.getCurrentUrl().equals("https://rahulshettyacademy.com/client/dashboard/cart"));

        CheckOutPage checkOutPage = cartPage.clickOnCheckoutBtn();
        assertTrue(driver.getCurrentUrl().contains("https://rahulshettyacademy.com/client/dashboard/order"));

        checkOutPage.chooseCountry(testingCountry);
        ConfirmationPage confirmationPage = checkOutPage.clickOnSubmit();
        orderID= confirmationPage.getOrderId();
        assertEquals(confirmationPage.getThankYouMsg(),"THANKYOU FOR THE ORDER.");
        assertEquals(confirmationPage.getToastMsg(),"Order Placed Successfully");
    }

    //verify order is displayed in Orders page
    @Test(dependsOnMethods = "submitOrder")
    public void orderHistoryTest()
    {
        landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );
        OrdersPage ordersPage = landingPage.openOrdersPage();
        assertTrue(ordersPage.verifyOrderIDdisplayed(orderID));
    }

    @Test(dataProvider = "getData")
    public void submitOrderWithDataProvider(String productToTest , String testingCountry) throws IOException
    {
        ProductCatalogue productCatalogue = landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );

        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(productToTest);

        CartPage cartPage= productCatalogue.openCartPage();
        assertTrue(cartPage.verifyProductDisplayInCartPage(productToTest));
        assertTrue(driver.getCurrentUrl().equals("https://rahulshettyacademy.com/client/dashboard/cart"));

        CheckOutPage checkOutPage = cartPage.clickOnCheckoutBtn();
        assertTrue(driver.getCurrentUrl().contains("https://rahulshettyacademy.com/client/dashboard/order"));

        checkOutPage.chooseCountry(testingCountry);
        ConfirmationPage confirmationPage = checkOutPage.clickOnSubmit();
        orderID= confirmationPage.getOrderId();
        assertEquals(confirmationPage.getThankYouMsg(),"THANKYOU FOR THE ORDER.");
        assertEquals(confirmationPage.getToastMsg(),"Order Placed Successfully");
    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{{"ZARA COAT 3" , "Germany"}, {"ADIDAS ORIGINAL" , "India"}, {"IPHONE 13 PRO" , "Egypt"}};
    }

    @Test(dataProvider = "getDataInHashMap")
    public void submitOrderWithDataProviderHashMap(HashMap<String,String> testData) throws IOException
    {
        ProductCatalogue productCatalogue = landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );

        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(testData.get("productName"));

        CartPage cartPage= productCatalogue.openCartPage();
        assertTrue(cartPage.verifyProductDisplayInCartPage(testData.get("productName")));
        assertTrue(driver.getCurrentUrl().equals("https://rahulshettyacademy.com/client/dashboard/cart"));

        CheckOutPage checkOutPage = cartPage.clickOnCheckoutBtn();
        assertTrue(driver.getCurrentUrl().contains("https://rahulshettyacademy.com/client/dashboard/order"));

        checkOutPage.chooseCountry(testData.get("testingCountry"));
        ConfirmationPage confirmationPage = checkOutPage.clickOnSubmit();
        orderID= confirmationPage.getOrderId();
        assertEquals(confirmationPage.getThankYouMsg(),"THANKYOU FOR THE ORDER.");
        assertEquals(confirmationPage.getToastMsg(),"Order Placed Successfully");
    }

    @DataProvider
    public Object[][] getDataInHashMap() {
        HashMap<String,String> dataMap= new HashMap<String,String>();
        dataMap.put("productName","ZARA COAT 3");
        dataMap.put("testingCountry","Germany");

        HashMap<String,String> dataMap2= new HashMap<String,String>();
        dataMap2.put("productName","ADIDAS ORIGINAL");
        dataMap2.put("testingCountry","India");

        HashMap<String,String> dataMap3= new HashMap<String,String>();
        dataMap3.put("productName","IPHONE 13 PRO");
        dataMap3.put("testingCountry","Egypt");

        return new Object[][]{{dataMap}, {dataMap2},{dataMap3}};
    }


    @Test
    public void submitOrderWithDataFromJsonFile() throws IOException
    {
        ProductCatalogue productCatalogue = landingPage.loginApplication(property.getProperty("validUserName"),property.getProperty("validPassword") );

        productCatalogue.getProductsList();
        productCatalogue.addProductToCart(getJsonData("productName"));

        CartPage cartPage= productCatalogue.openCartPage();
        assertTrue(cartPage.verifyProductDisplayInCartPage(getJsonData("productName")));
        assertTrue(driver.getCurrentUrl().equals("https://rahulshettyacademy.com/client/dashboard/cart"));

        CheckOutPage checkOutPage = cartPage.clickOnCheckoutBtn();
        assertTrue(driver.getCurrentUrl().contains("https://rahulshettyacademy.com/client/dashboard/order"));

        checkOutPage.chooseCountry(getJsonData("testingCountry"));

        System.out.println(getJsonData("testingCountry"));
        ConfirmationPage confirmationPage = checkOutPage.clickOnSubmit();
        orderID= confirmationPage.getOrderId();
        assertEquals(confirmationPage.getThankYouMsg(),"THANKYOU FOR THE ORDER.");
        assertEquals(confirmationPage.getToastMsg(),"Order Placed Successfully");
    }

}
