package automationPractice;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

public class HomePageAutomationPractice extends BasePageObject {
    private final By signInLinkLocator;

    public HomePageAutomationPractice(WebDriver driver, Logger log) {
        super(driver, log, "https://automationpractice.com/index.php");
        signInLinkLocator = By.xpath("//a[@class='signIn']");
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
}