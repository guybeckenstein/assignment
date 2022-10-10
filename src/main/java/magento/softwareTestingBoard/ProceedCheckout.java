package magento.softwareTestingBoard;

import base.BasePageObject;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProceedCheckout extends BasePageObject {
    // First page
    private final By streetAddress;
    private final By city;
    private final By state;
    private final By postalCode;
    private final By country;
    private final By phoneNumber;
    private final By shipMethodBest;
    private final By shipMethodFlat;
    private final By nextButton;
    // Second page
    private final By placeOrderButton;
    // Third page
    private final By orderNumber;
    public ProceedCheckout(WebDriver driver, Logger log) {
        super(driver, log, "https://magento.softwaretestingboard.com/checkout/#shipping");
        streetAddress = By.xpath("//input[@name='street[0]']");
        city = By.xpath("//input[@name='city']");
        state = By.xpath("//select[@name='region_id']");
        postalCode = By.xpath("//input[@name='postcode']");
        country = By.xpath("//select[@name='country_id']");
        phoneNumber = By.xpath("//input[@name='telephone']");
        shipMethodBest = By.xpath("//input[@name='ko_unique_1']");
        shipMethodFlat = By.xpath("//input[@name='ko_unique_2']");
        nextButton = By.xpath("//button[@data-role='opc-continue']");
        placeOrderButton = By.xpath("//div[@class='actions-toolbar']/div/button[@class='action primary checkout']");
        orderNumber = By.className("checkout-success");
    }

    public void firstPage(String streetAddress, String city, String state, String postalCode, String country, String phoneNumber, boolean cheapShip) {
        // Add checkout parameters
        type(streetAddress, this.streetAddress);
        type(city, this.city);
        selectDropdownOption(state, this.state);
        type(postalCode, this.postalCode);
        selectDropdownOption(country, this.country);
        type(phoneNumber, this.phoneNumber);
        if (cheapShip) {
            click(shipMethodBest);
        } else {
            click(shipMethodFlat);
        }

        // Proceed to next page
        click(nextButton);

        pageUrl = "https://magento.softwaretestingboard.com/checkout/#payment";
    }

    private void selectDropdownOption(String choice, By dropdownLocator) {
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

    public void secondPage() {
        click(placeOrderButton);

        pageUrl = "https://magento.softwaretestingboard.com/checkout/onepage/success/";
    }

    public boolean success() {
        return driver.findElement(orderNumber).getText().contains("Your order number is:");
    }
}
