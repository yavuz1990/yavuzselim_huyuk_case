package uiTest.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WaitUtils {
    public static void hoverOverElement(WebDriver driver, WebElement element) {
        String browserName = ((org.openqa.selenium.remote.RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase();

        if (browserName.contains("firefox")) {
            // Firefox için JavaScript hover
            String script = "var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true}); arguments[0].dispatchEvent(event);";
            ((JavascriptExecutor) driver).executeScript(script, element);
        } else {
            // Chrome ve diğer tarayıcılar için Actions sınıfı
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        }
    }
}
