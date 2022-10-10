package magento.softwareTestingBoard;

import base.TestUtilities;
import org.testng.annotations.Test;

public class CreateAccountTest extends TestUtilities {

    @Test(priority = 1)
    public void createAccountTest() {
        HomePageSoftwareTestingBoard homePage = new HomePageSoftwareTestingBoard(driver, log);
        homePage.openPage();

        /** Click on sign in link **/
        CreateAccount createAccountPage = homePage.clickCreateAnAccountLink();

        /** Create new account **/
        AccountPage accountPage = createAccountPage.createNewAccount("Guy", "Beckenstein", "guybecken@gmail.com", "1a2b3c4D", true);

        /** Check if message appears **/
        String expectedMessage = "Thank you for registering with Fake Online Clothing Store.";
        softAssert.assertTrue(accountPage.compareMessageToExpectedMessage(expectedMessage), "ERROR: wrong successful message!");

        /** Click menu toggle **/
        softAssert.assertTrue(accountPage.clickMenuToggle(), "ERROR: did not find menu toggle!");

        /** Sign out **/
        softAssert.assertTrue(accountPage.clickSignOut(), "ERROR: could not sign out!");

    }
}
