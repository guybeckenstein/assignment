package magento.softwareTestingBoard;

import base.TestUtilities;
import org.testng.annotations.Test;

public class SignInTest extends TestUtilities {
    @Test(priority = 1)
    public void positiveSignInTest() throws InterruptedException {
        HomePageSoftwareTestingBoard homePage = new HomePageSoftwareTestingBoard(driver, log);
        homePage.openPage();

        /** Click on sign in link **/
        SignIn signInPage = homePage.clickSignInLink();

        /** Sign in **/
        homePage = signInPage.positiveSignIn("guybecken@gmail.com", "1a2b3c4D");

        /** Add items to cart **/
        homePage = homePage.addItemToCart(1, "XS", "Purple");
        homePage = homePage.addItemToCart(3, "XL", "Gray");

        /** Proceed checkout **/
        ProceedCheckout proceedCheckoutPage = homePage.proceedCheckout();

        /** Continue **/
        proceedCheckoutPage.firstPage("Address 1", "Givatayim", "Alabama", "12345",
                "United States", "0500000000", true);
        proceedCheckoutPage.secondPage();
        softAssert.assertTrue(proceedCheckoutPage.success(), "ERROR: did not make purchase!");

        /** Click menu toggle **/
        softAssert.assertTrue(homePage.clickMenuToggle(), "ERROR: did not find menu toggle!");

        /** Sign out **/
        softAssert.assertTrue(homePage.clickSignOut(), "ERROR: could not sign out!");
    }
}
