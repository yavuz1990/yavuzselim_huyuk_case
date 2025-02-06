package uiTest.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageUtils {
    public static void acceptCookies(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement acceptCookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wt-cli-accept-all-btn\"]"))
            );
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("Çerez penceresi bulunamadı veya zaten kapalı.");
        }
    }

    public static void checkNotificationPermission(WebDriver driver) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String notificationPermission = (String) jsExecutor.executeScript("return Notification.permission;");
            if (!"granted".equals(notificationPermission)) {
                System.out.println("Bildirim izni verilmedi.");
            }
        } catch (Exception e) {
            System.out.println("Bildirim kontrolü sırasında hata oluştu: " + e.getMessage());
        }
    }

    public static void waitForPageAndAjaxToLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> {
            if (webDriver == null) {
                throw new IllegalStateException("WebDriver null olarak tespit edildi.");
            }

            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            boolean pageReady = "complete".equals(jsExecutor.executeScript("return document.readyState"));
            boolean ajaxComplete = Boolean.TRUE.equals(jsExecutor.executeScript(
                    "return (typeof jQuery !== 'undefined') ? jQuery.active == 0 : true;"
            ));
            return pageReady && ajaxComplete;
        });
        System.out.println("Sayfa ve tüm AJAX çağrıları yüklendi.");
    }

}
