package automationPractice;

import base.BasePageObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignIn extends BasePageObject {
    /** Sign in **/
    private final By emailLocator;
    private final By passwordLocator;
    private final By loginButtonLocator;
    private final By amountErrorsLocator;
    private final By errorsDetailsLocator; // May not work as expected (list)
    /** Sign up **/
    private final By createAccountEmailLocator;
    private final By createAccountButtonLocator;

    public SignIn(WebDriver driver, Logger log) {
        super(driver, log, "http://automationpractice.com/index.php?controller=authentication#account-creation");
        emailLocator = By.id("email");
        passwordLocator = By.xpath("//input[@type='password']");
        loginButtonLocator = By.xpath("//button[@id='SubmitLogin']");
        amountErrorsLocator = By.xpath("//div[@class='alert alert-danger']/p");
        errorsDetailsLocator = By.xpath("//div[@class='alert alert-danger']/ol/li");
        createAccountEmailLocator = By.xpath("//input[@class='is_required validate account_input form-control']");
        createAccountButtonLocator = By.xpath("//button[@class='btn btn-default button button-medium exclusive']");
    }

    /** Execute sign up **/
    public SignUp goToSignUp(String emailAddress) {
        log.printf(Level.INFO, "Executing sign up with email address [%s]", emailAddress);
        type(emailAddress, createAccountEmailLocator);
        click(createAccountButtonLocator);

        return new SignUp(driver, log);
    }

    /** Execute sign in **/
    public SignedIn signIn(String email, String password) {
        log.info("Executing signIn with email [" + email + "] and password [" + password + "]");
        type(email, emailLocator);
        type(password, passwordLocator);
        click(loginButtonLocator);

        return new SignedIn(driver, log);
    }

    /** Execute sign in fail **/
/*    public void negativeLogin(String username, String password) {
        log.info("Executing signIn fail with username [" + username + "] and password [" + password + "]");
        type(username, emailLocator);
        type(password, passwordLocator);
        click(loginButtonLocator);
    }*/

    /** Get signIn error message **/
/*    public String getErrorMessage(By locator) {
        log.info("Receiving error message.");
        return getMessage(locator);
    }*/
}
