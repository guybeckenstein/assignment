package base.interfaces;

import org.openqa.selenium.*;

public interface BasePageInterface {
    String getCurrentPageSource(); /** Gets source of current page **/
    void pressKeyWithActions(Keys key); /** Presses Key using Actions class - without parameters of the actual page **/
    String getPageUrl(); /** Gets URL variable from pageObject **/
    String getCurrentUrl(); /** Gets URL of current page from the browser **/
    boolean compareUrlToExpectedUrl(String expectedUrl); /** Compares expected page URL to given page URL **/
    void scrollToBottom(); /** Performs scroll to the bottom **/
    void setCookie(Cookie ck); /** Adds cookie **/
    String getCookie(String name); /** Gets cookie value using cookie name **/
}
