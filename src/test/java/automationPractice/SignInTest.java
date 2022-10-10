package automationPractice;

import base.CsvDataProviders;
import base.TestUtilities;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import java.util.Map;

public class SignInTest extends TestUtilities {
    private HomePageAutomationPractice welcomePage;

    @Test(priority = 1, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void positiveSignInTest(Map<String, String> testData) {
        log.info("Starting negativeSignInTest #" + testData.get("num") + " for " + testData.get("description"));
        openMainPage();
        // takeScreenshot("WelcomePage opened");

        /** Click on sign in link **/
        SignIn signInPage = welcomePage.clickSignInLink();
        // takeScreenshot("LoginPage opened");

        /** Execute sign in **/
        SignedIn signedInPage = signInPage.signIn(testData.get("email"), testData.get("password"));

        /** Add new cookie **/
        Cookie ck = new Cookie(testData.get("email"), testData.get("password"), "automationpractice.com", "/", null);
        signInPage.setCookie(ck);

        /*/*//** Get cookies **//*
        *//*String username = secureAreaPage.getCookie("username");
        log.info("Username cookie: " + username);
        String session = secureAreaPage.getCookie("rack.session");
        log.info("Session cookie: " + session);*//*

        *//** Verifications: New URL ; Logout button is visible ; Successful signIn message **//*
        softAssert.assertEquals(secureAreaPage.getCurrentUrl(), secureAreaPage.getPageUrl());
        softAssert.assertTrue(secureAreaPage.isLogOutButtonVisible(), "Logout button is invisible.");
        String expectedSuccessMessage = "You logged into a secure area!";
        String actualSuccessMessage = secureAreaPage.getSuccessMessageText();
        softAssert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
                "'actualSuccessMessage' does not contain 'expectedSuccessMessage'!\n" +
                        "'expectedSuccessMessage': " + expectedSuccessMessage + "\n" +
                        "'actualSuccessMessage': " + actualSuccessMessage);*/
    }

    @Test(priority = 2, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void negativeSignInTest(Map<String, String> testData) {
        log.info("Starting negativeSignInTest #" + testData.get("num") + " for " + testData.get("description"));

        openMainPage();

        // Click on Form Authentication link
        SignIn signInPage = welcomePage.clickSignInLink();
        /*
        // Execute negative signIn failure
        signInPage.negativeLogin(testData.get("username"), testData.get("password"));

        // Wait for error message
        String actualErrorMessage = signInPage.getErrorMessage(By.id("flash"));

        // Verification
        softAssert.assertTrue(actualErrorMessage.contains(testData.get("expectedMessage")), "ERROR: Message doesn't contain expected text!");*/
    }

    private void openMainPage() {
        /** Open main page **/
        welcomePage = new HomePageAutomationPractice(driver, log);
        welcomePage.openPage();
    }
}
