package selfPracticing.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports getReportObject()
    {
        String path = System.getProperty("user.dir")+"//Reports//report.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(path);
        extentSparkReporter.config().setReportName("Self Practicing Project");
        extentSparkReporter.config().setDocumentTitle("Automation Report");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Tester" , "Sara Bayoumy");

        return extentReports;
    }
}
