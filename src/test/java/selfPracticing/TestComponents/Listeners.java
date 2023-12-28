package selfPracticing.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import selfPracticing.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest extentTest;
    ExtentReports extentReports = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
        threadLocal.set(extentTest);  //To get unique thread id for each extent test
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        threadLocal.get().log(Status.PASS , "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath=null;
        ITestListener.super.onTestFailure(result);
        threadLocal.get().fail(result.getThrowable());
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            screenshotPath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        threadLocal.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName()+".png");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extentReports.flush();
    }
}
