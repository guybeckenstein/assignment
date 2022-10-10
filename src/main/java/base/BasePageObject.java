package base;

import base.interfaces.BasePageInterface;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePageObject implements BasePageInterface {
    protected String pageUrl;
    protected final WebDriver driver;
    protected final Logger log;

    public BasePageObject(WebDriver driver, Logger log, String pageUrl) {
        this.driver = driver;
        this.log = log;
        this.pageUrl = pageUrl;
    }

    /** Opens page with the given URL **/
    protected void openUrl(String url) {
        driver.get(url);
    }

    /** Finds element using given locator **/
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    /** Finds all elements using given locator **/
    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }



    /** Clicks on element with given locator **/
    protected void click(By locator) {
        waitForVisibilityOf(locator, 5);
        find(locator).click();
    }

    protected void clickNoWait(By locator) {
        find(locator).click();
    }

    /** Types given text into element with the given locator **/
    protected void type(String text, By locator) {
        waitForVisibilityOf(locator, 5);
        find(locator).sendKeys(text);
    }

    /** Gets message text from a locator of current page **/
    protected String getMessage(By locator) {
        waitForVisibilityOf(locator, 5);
        return find(locator).getText();
    }

    /** Waits for specific ExpectedCondition for the given amount of time in seconds **/
    private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(condition);
    }

    /** Waits for the given number of seconds for element with given locator to be visible on the page **/
    protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    /** Waits for alert to present and then switches to it **/
    protected Alert switchToAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }

    /** Switches to a new window **/
    protected void switchToWindowWithTitle(String expectedTitle) {
        // Switching to new window
        String firstWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(firstWindow)) {
                driver.switchTo().window(window);
                if (getCurrentPageTitle().equals(expectedTitle)) {
                    break;
                }
            }
        }
    }

    /** Gets title of current page **/
    private String getCurrentPageTitle() {
        return driver.getTitle();
    }

    /** Gets source of current page **/
    public String getCurrentPageSource() {
        return driver.getPageSource();
    }

    /** Switches to iFrame using its locator **/
    protected void switchToFrame(By frameLocator) {
        driver.switchTo().frame(find(frameLocator));
    }

    /** Presses Key on locator - with 'locator' - parameter of the actual page **/
    protected void pressKey(By locator, Keys key) {
        find(locator).sendKeys(key);
    }

    /** Presses Key using Actions class - without parameters of the actual page **/
    public void pressKeyWithActions(Keys key) {
        log.info("Pressing " + key.name() + " using Actions class");
        Actions action = new Actions(driver);
        action.sendKeys(key).build().perform(); // Generic press key method, WebElement is not needed
    }

    /** Gets URL variable from pageObject **/
    public String getPageUrl() {
        return pageUrl;
    }

    /** Gets URL of current page from the browser **/
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Compares expected page URL to given page URL **/
    public boolean compareUrlToExpectedUrl(String expectedUrl) {
        return driver.getCurrentUrl().equalsIgnoreCase(expectedUrl);
    }


    /** Performs scroll to the bottom **/
    public void scrollToBottom() {
        log.info("Scrolling to the bottom of the page.");
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /** Drags 'from' element to 'to' element **/
    protected void performDragAndDrop(By from, By to) {
        // Workaround that works from: https://stackoverflow.com/a/62809056/3598990
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(
                "function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
                        + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
                        + "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                        + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n"
                        + "return event;\n" + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                        + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                        + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                        + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n"
                        + "}\n" + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                        + "var dragStartEvent =createEvent('dragstart');\n"
                        + "dispatchEvent(element, dragStartEvent);\n" + "var dropEvent = createEvent('drop');\n"
                        + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                        + "var dragEndEvent = createEvent('dragend');\n"
                        + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                        + "var source = arguments[0];\n" + "var destination = arguments[1];\n"
                        + "simulateHTML5DragAndDrop(source,destination);",
                find(from), find(to));
    }

    /** Performs mouse hover over element **/
    protected void hoverOverElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().build().perform();
    }

    /** Adds cookie **/
    public void setCookie(Cookie ck) {
        log.info("Adding cookie " + ck.getName());
        driver.manage().addCookie(ck);
        log.info("Cookie added");
    }

    /** Gets cookie value using cookie name **/
    public String getCookie(String name) {
        log.info("Getting value of cookie " + name);
        return driver.manage().getCookieNamed(name).getValue();
    }
}
