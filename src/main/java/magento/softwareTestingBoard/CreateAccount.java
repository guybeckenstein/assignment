package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateAccount extends BasePageObject {
    private final By firstNameLocator;
    private final By lastNameLocator;
    private final By emailLocator;
    private final By passwordLocator;
    private final By confirmLocator;
    private final By newsletterLocator;
    private final By createAccountButtonLocator;

    public CreateAccount(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/customer/account/create/");
        firstNameLocator = By.id("firstname");
        lastNameLocator = By.id("lastname");
        emailLocator = By.id("email_address");
        passwordLocator = By.id("password");
        confirmLocator = By.id("password-confirmation");
        newsletterLocator = By.id("is_subscribed");
        createAccountButtonLocator = By.linkText("Create an Account");
    }

    public AccountPage createNewAccount(String firstName, String lastName, String email, String password, boolean newsletter) {
        type(firstName, firstNameLocator);
        type(lastName, lastNameLocator);
        type(email, emailLocator);
        type(password, passwordLocator);
        type(password, confirmLocator);
        if (newsletter) {
            click(newsletterLocator);
        }

        click(createAccountButtonLocator);
        return new AccountPage(driver, log);
    }
}
