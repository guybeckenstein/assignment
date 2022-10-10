package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePageObject {
    private final By successMessage;
    private final By menuToggle;
    private final By signOut;

    public AccountPage(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/customer/account/");
        successMessage = By.xpath("//div[@role='alert']/div/div");
        menuToggle = By.xpath("//span[@class='customer-name']");
        signOut = By.linkText("Sign Out");
    }

    public boolean compareMessageToExpectedMessage(String message) {
        return getMessage(successMessage).contains(message);
    }

    public boolean clickMenuToggle() {
        try {
            click(menuToggle);
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean clickSignOut() {

        try {
            click(signOut);
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }
}
