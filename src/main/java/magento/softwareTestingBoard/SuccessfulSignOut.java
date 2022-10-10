package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessfulSignOut extends BasePageObject {
    private final By expectedMessage;
    public SuccessfulSignOut(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/customer/account/logoutSuccess/");
        expectedMessage = By.linkText("You have signed out and will go to our homepage in 5 seconds.");
    }

    public boolean compareTexts(String msg) {
        return getMessage(expectedMessage).contains(msg);
    }
}
