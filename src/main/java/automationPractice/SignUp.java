package automationPractice;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.InputMismatchException;

public class SignUp extends BasePageObject {
    /** Personal Information **/
    private final By genderMaleLocator, genderFemaleLocator;
    private final By customerFirstNameLocator;
    private final By customerLastNameLocator;
    private final By passwordLocator;
    private final By dateOfBirthDayLocator;
    private final By dateOfBirthMonthLocator;
    private final By dateOfBirthYearLocator;
    private final By newsletterLocator;
    /** Your Address **/
    private final By companyLocator;
    private final By addressLocator;
    private final By cityLocator;
    private final By stateLocator;
    private final By postcodeLocator;
    private final By countryLocator;
    private final By additionalInformationLocator;
    private final By mobilePhoneLocator;
    /** Register button **/
    private final By registerButtonLocator;

    public SignUp(WebDriver driver, Logger log) {
        super(driver, log, "http://automationpractice.com/index.php?controller=authentication");
        /** Personal Information **/
        genderMaleLocator = By.id("id_gender1");
        genderFemaleLocator = By.id("id_gender2");
        customerFirstNameLocator = By.id("customer_firstname");
        customerLastNameLocator = By.id("customer_lastname");
        passwordLocator = By.id("passwd");
        dateOfBirthDayLocator = By.id("days");
        dateOfBirthMonthLocator = By.id("months");
        dateOfBirthYearLocator = By.id("years");
        newsletterLocator = By.xpath("//input[@name='newsletter']");
        /** Your Address **/
        companyLocator = By.id("company");
        addressLocator = By.id("address1");
        cityLocator = By.id("city");
        stateLocator = By.id("id_state");
        postcodeLocator = By.id("postcode");
        countryLocator = By.id("id_country");
        additionalInformationLocator = By.id("other");
        mobilePhoneLocator = By.id("phone_mobile");
        /** Register button **/
        registerButtonLocator = By.id("submitAccount");
    }

    // Adding 1_000_000 parameters is bad
    public SignedIn clickRegisterLink(
            /** Personal Information **/
            String gender, String customerFirstName, String customerLastName, String password, int day, int month, int year, boolean acceptNewsletter,
            /** Your Address **/
            String company, String address, String city, String state, String postalCode, String country, String additionalInformation, String mobilePhone) {
        /** Personal Information **/
        if (gender.equals("Male")) {
            click(genderMaleLocator);
        } else if (gender.equals("Female")) {
            click(genderFemaleLocator);
        } else {
            throw new InputMismatchException("None of the genders was written: 'Male', 'Female'");
        }
        type(customerFirstName, customerFirstNameLocator);
        type(customerLastName, customerLastNameLocator);
        type(password, passwordLocator);
        selectDropdownOption(day, dateOfBirthDayLocator);
        selectDropdownOption(month, dateOfBirthMonthLocator);
        selectDropdownOption(Integer.toString(year), dateOfBirthYearLocator);
        // Not working
        if (acceptNewsletter) {
            clickNoWait(newsletterLocator);
        }
        /** Your Address **/
        type(company, companyLocator);
        type(address, addressLocator);
        type(city, cityLocator);
        selectDropdownOption(state, stateLocator);
        type(postalCode, postcodeLocator);
        selectDropdownOption(country, countryLocator);
        type(additionalInformation, additionalInformationLocator);
        type(mobilePhone, mobilePhoneLocator);

        click(registerButtonLocator);
        return new SignedIn(driver, log);
    }

    public void selectDropdownOption(int i, By dropdownLocator) {
        Select dropdown = createSelect("Selecting option " + i + " from dropdown", dropdownLocator);
        dropdown.selectByIndex(i);
    }

    public void selectDropdownOption(String choice, By dropdownLocator) {
        Select dropdown = createSelect("Selecting option " + choice + " from dropdown", dropdownLocator);
        if (Character.isAlphabetic(choice.charAt(0))) {
            dropdown.selectByVisibleText(choice);
        } else {
            dropdown.selectByValue(choice);
        }
    }

    private Select createSelect(String strToLog, By dropdown) {
        log.info(strToLog);
        WebElement dropdownElement = find(dropdown);

        // Only works if the dropdown is under 'select' tag in HTML
        return new Select(dropdownElement);
    }
}
