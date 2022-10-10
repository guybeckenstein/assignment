package base;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class BrowserDriverFactory {
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private final Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {

        // Create driver
        log.info("Create driver: " + browser);

        switch (browser) {
            // Regular web browser
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                ChromeOptions option = new ChromeOptions();
                option.setPageLoadStrategy(PageLoadStrategy.NONE);
                driver.set(new ChromeDriver(option));
                break;
            // Regular web browser
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver());
                break;
            // Headless web browser
            case "chrome-headless":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");

                driver.set(new ChromeDriver(chromeOptions));
                break;
            // Headless web browser
            case "firefox-headless":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);

                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            // Headless web browser
            case "htmlunit":
                driver.set(new HtmlUnitDriver());
                break;
            default:
                throw new InputMismatchException("Do not know how to start: " + browser + ", starting chrome.");
        }
        driver.get().manage().window().maximize();

        return driver.get();
    }
}
