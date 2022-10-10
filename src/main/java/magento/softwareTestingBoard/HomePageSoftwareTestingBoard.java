package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class HomePageSoftwareTestingBoard extends BasePageObject {
    private final By signInLinkLocator;
    private final By createAnAccountLinkLocator;
    private final By menuToggle;
    private final By signOut;

    private final By hotSellers;

    public HomePageSoftwareTestingBoard(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/");
        signInLinkLocator = By.linkText("Sign In");
        createAnAccountLinkLocator = By.linkText("Create an Account");

        menuToggle = By.xpath("//span[@class='customer-name']");
        signOut = By.linkText("Sign Out");

        hotSellers = By.linkText("Hot Sellers");
    }

    /** Open WelcomePage with its URL **/
    public void openPage() {
        log.info("Opening page: " + pageUrl);
        openUrl(pageUrl);
        log.info("Page has opened!");
    }

    /** Opening page, using reflection operations **/
    protected Object clickLink(By linkLocator, Class<?> clazz) {
        log.info("Clicking " + linkLocator.toString().substring(13) + " link on Home Page (index.html)");
        click(linkLocator);
        try {
            Object obj = clazz.getConstructor(WebDriver.class, Logger.class).newInstance(driver, log);
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /** Opening signIn page **/
    public SignIn clickSignInLink() {
        return (SignIn)clickLink(signInLinkLocator, SignIn.class);
    }

    /** Opening CreateAnAccount page **/
    public CreateAccount clickCreateAnAccountLink() {
        return (CreateAccount)clickLink(createAnAccountLinkLocator, CreateAccount.class);
    }

    public boolean clickMenuToggle() {
        try {
            click(menuToggle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickSignOut() {

        try {
            click(signOut);
            log.info("Signed out successfully!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HomePageSoftwareTestingBoard addItemToCart(int idx, String size, String color) throws InterruptedException {
        Thread.sleep(2000);

        /** Did not manage to use generic xpath **/
        // Choose size + click
        WebElement cartElement = driver.findElements(By.xpath("//li[@class='product-item']")).get(idx - 1);
        WebElement cartSize = cartElement.findElement(By.xpath("//div[@aria-label='" + size + "']"));
        cartSize.click();

        // Choose color + click
        WebElement cartColor = cartElement.findElement(By.xpath("//div[@aria-label='" + color + "']"));
        cartColor.click();

        // Add to cart
        try {
            cartElement.findElement(By.xpath("//button[@title='Add to Cart']")).click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", cartElement.findElement(By.xpath("//button[@title='Add to Cart']")) );
        }
        log.info("Successfully added item to cart!");

        Thread.sleep(2000);
        return new HomePageSoftwareTestingBoard(driver, log);
    }

    public ProceedCheckout proceedCheckout() {
        /** Show cart **/
        waitForVisibilityOf(By.xpath("//a[@class='action showcart']"));
        driver.findElement(By.xpath("//a[@class='action showcart']")).click();

        /** Go to checkout **/
        waitForVisibilityOf(By.id("top-cart-btn-checkout"));
        driver.findElement(By.id("top-cart-btn-checkout")).click();

        return new ProceedCheckout(driver, log);
    }
}