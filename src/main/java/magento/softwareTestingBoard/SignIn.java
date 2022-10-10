package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignIn extends BasePageObject {
    private final By emailLocator;
    private final By passwordLocator;
    private final By signInButtonLocator;

    public SignIn(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/");
        emailLocator = By.id("email");
        passwordLocator = By.xpath("//input[@id='pass']");
        signInButtonLocator = By.id("send2");
    }

    /** Execute sign in **/
    public HomePageSoftwareTestingBoard positiveSignIn(String email, String password) {
        type(email, emailLocator);
        type(password, passwordLocator);
        click(signInButtonLocator);

        return new HomePageSoftwareTestingBoard(driver, log);
    }
}
