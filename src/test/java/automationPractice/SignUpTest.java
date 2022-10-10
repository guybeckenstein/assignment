package automationPractice;

import base.CsvDataProviders;
import base.TestUtilities;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import java.util.Map;

public class SignUpTest extends TestUtilities {
    private HomePageAutomationPractice welcomePage;

    @Test
    public void signUpTest() throws InterruptedException {
        /** Open main page **/
        welcomePage = new HomePageAutomationPractice(driver, log);
        welcomePage.openPage();

        /** Click on sign in link **/
        SignIn signInPage = welcomePage.clickSignInLink();

        /** Execute sign up **/
        SignUp signUpPage = signInPage.goToSignUp("guybecken@gmail.com");
        Thread.sleep(5000);
        /** Execute register **/
        SignedIn signedInPage = signUpPage.clickRegisterLink(
                "Male", "Guy", "Beckenstein", "12345", 1, 1, 1990, true,
                "Student", "Katzenelson", "Givatayim", "Alabama", "12345",
                "United States", "", "050000000");

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
}
