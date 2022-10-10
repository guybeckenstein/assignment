package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class BaseTest {
    protected final SoftAssert softAssert = new SoftAssert();
    protected WebDriver driver;
    protected Logger log;
    protected String testSuiteName;
    protected String testName;
    protected String testMethodName;

    @Parameters({ "browser", "deviceName" })
    @BeforeMethod(alwaysRun = true)
    protected void setUp(Method method, @Optional("chrome") String browser, @Optional String deviceName, ITestContext ctx) {
        String testName = ctx.getCurrentXmlTest().getName();
        log = LogManager.getLogger(testName);

        BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);

        driver = factory.createDriver();

        driver.manage().window().maximize();

        testSuiteName = ctx.getSuite().getName();
        this.testName = testName;
        testMethodName = method.getName();
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        log.info("Close driver");
        // Close browser
        driver.quit();

        softAssert.assertAll();
    }
}
