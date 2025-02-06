package uiTest.pages;

import uiTest.utils.JavaScriptUtils;
import uiTest.utils.PageUtils;
import uiTest.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JobListingsPage {
    private final WebDriver driver;
    private final By jobList = By.id("jobs-list");
    private final By viewRoleButton = By.xpath("//a[text()='View Role']");
    private final By locationFilterInput = By.xpath("//*[@id=\"top-filter-form\"]/div[1]/span");
    private final By locationDropDown = By.xpath("//*[@id=\"top-filter-form\"]/div[1]/span");
    private final By locationOption = By.xpath("//li[contains(@id,'select2-filter-by-location-result') and contains(text(),'Istanbul, Turkiye')]");
    private final By departmentDropdown = By.id("select2-filter-by-department-container");
    private final By departmentOption = By.xpath("//li[contains(@id,'select2-filter-by-department-result') and contains(text(),'Quality Assurance')]");

    public JobListingsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void filterJobs() {
        // Filtreleme işlemi öncesinde sayfanın ve elementlerin tamamen yüklendiğinden emin olun
        PageUtils.waitForPageAndAjaxToLoad(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // "Location" açılır menüsünden seçim
        try {
            WebElement locationDropdown1 = wait.until(
                    ExpectedConditions.elementToBeClickable(locationDropDown)
            );
            locationDropdown1.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    locationOption
            ));
            System.out.println("Location seçenekleri yüklendi.");

            WebElement locationOption1 = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            locationOption
                    )
            );
            locationOption1.click();
            System.out.println("'Istanbul, Turkiye' seçildi.");
        } catch (Exception e) {
            System.out.println("'Location' açılır menüsünden seçim sırasında hata oluştu: " + e.getMessage());
        }

        // "Department" açılır menüsünden seçim
        try {
            WebElement departmentDropdown1 = wait.until(
                    ExpectedConditions.elementToBeClickable(departmentDropdown)
            );
            departmentDropdown1.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    departmentOption
            ));
            System.out.println("Department seçenekleri yüklendi.");

            WebElement departmentOption1 = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            departmentOption
                    )
            );
            departmentOption1.click();
            System.out.println("'Quality Assurance' seçildi.");
        } catch (Exception e) {
            System.out.println("'Department' açılır menüsünden seçim sırasında hata oluştu: " + e.getMessage());
        }
    }

    public boolean isJobListVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Önce jobList elementini locate ediyoruz:
        WebElement element = driver.findElement(jobList);

        // Scroll işlemi yardımcı klasa taşındı ve burada çağrıldı
        //JavaScriptUtils.scrollElementIntoView(driver, element);
        JavaScriptUtils.clickWithJS(driver, element);
        wait.until(ExpectedConditions.visibilityOf(element));

        return !driver.findElements(jobList).isEmpty();
    }

    public boolean areAllJobsCorrect() {
        PageUtils.waitForPageAndAjaxToLoad(driver);
        List<WebElement> jobs = driver.findElements(jobList);
        for (WebElement job : jobs) {
            String jobText = job.getText();
            if (!jobText.contains("Quality Assurance") || !jobText.contains("Istanbul, Turkiye")) {
                return false;
            }
        }
        return true;
    }

    public void clickViewRoleButton() {
        // Önce elementi locate edip WebElement elde ediyoruz:
        WebElement element = driver.findElement(viewRoleButton);
        // Sonra hover işlemi için elde edilen element'i kullanıyoruz:
        WaitUtils.hoverOverElement(driver, element);
        // Element'e tıklıyoruz:
        element.click();
    }
}
