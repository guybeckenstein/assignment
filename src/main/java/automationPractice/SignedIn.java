package automationPractice;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SignedIn extends BasePageObject {
    public SignedIn(WebDriver driver, Logger log) {
        super(driver, log, "http://automationpractice.com/index.php?controller=my-account");
    }
}
