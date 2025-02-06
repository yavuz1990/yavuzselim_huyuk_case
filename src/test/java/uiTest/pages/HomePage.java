package uiTest.pages;

import uiTest.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By companyMenu = By.xpath("//a[normalize-space()='Company']");
    private final By careersLink = By.xpath("//a[normalize-space()='Careers']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToCareers() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            // Çerezleri ve bildirim izinlerini kontrol et
            PageUtils.acceptCookies(driver);
            PageUtils.checkNotificationPermission(driver);


            // Company menüsüne tıkla
            waitForElementToBeClickable(companyMenu).click();

            // Careers linkine tıkla
            waitForElementToBeClickable(careersLink).click();

            // URL kontrolü
            if (!Objects.requireNonNull(driver.getCurrentUrl()).contains("careers")) {
                throw new RuntimeException("Kariyer sayfasına yönlendirme başarısız oldu!");
            }
        } catch (Exception e) {
            System.err.println("navigateToCareers sırasında hata oluştu: " + e.getMessage());
            throw e;
        }
    }

    public boolean isHomePageOpened() {
        return Objects.requireNonNull(driver.getTitle()).contains("Leader");
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
}
