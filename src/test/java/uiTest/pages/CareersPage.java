package uiTest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class CareersPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By seeAllQaJobsButton = By.xpath("//*[@id='page-head']/div/div/div[1]/div/div/a");
    private final By ourLocationsHeader = By.xpath("//*[@id=\"location-slider\"]/div/ul");
    private final By teamsHeader = By.xpath("/html/body/div[2]/div[2]");
    private final By lifeHeader = By.xpath("/html/body/div[2]/section[4]");

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("careers");
    }

    public boolean isOurLocationsVisible() {
        return isSectionVisible(ourLocationsHeader);
    }

    public boolean isTeamsVisible() {
        return isSectionVisible(teamsHeader);
    }

    public boolean isLifeVisible() {
        return isSectionVisible(lifeHeader);
    }

    public void clickSeeAllQaJobs() {
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQaJobsButton)).click();
    }

    private boolean isSectionVisible(By sectionLocator) {
        try {
            WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(sectionLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
            return section.isDisplayed();
        } catch (Exception e) {
            System.out.println("Bölüm görünür değil: " + e.getMessage());
            return false;
        }
    }
}
