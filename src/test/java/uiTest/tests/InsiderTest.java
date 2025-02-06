package uiTest.tests;

import uiTest.pages.CareersPage;
import uiTest.pages.HomePage;
import uiTest.pages.JobListingsPage;
import uiTest.utils.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
@Listeners(uiTest.utils.TestListener.class) // Listener sınıfı tanımlandı
public class InsiderTest extends BaseTest {
// Test 1: Ana sayfayı ziyaret et ve açıldığını kontrol et
@Test(priority = 1)
public void testHomePageOpened() {
    driver.get("https://useinsider.com/");
    HomePage homePage = new HomePage(driver);
    SoftAssert softAssert = new SoftAssert();

    // Eğer bu assertion başarısız olursa, test metodu çalışmaya devam edecek
    softAssert.assertTrue(homePage.isHomePageOpened(), "Home page çalışmıyor veya doğru açılmıyor!");

    // Tüm soft assertion'ları kontrol et (fail varsa burada raporlanır)
    softAssert.assertAll();
}

// Test 2: Kariyer sayfasına git ve gerekli bölümlerin yüklendiğini kontrol et
@Test(priority = 2)
public void testNavigateToCareersPage(){
    HomePage homePage = new HomePage(driver);
    homePage.navigateToCareers();
    SoftAssert softAssert = new SoftAssert();

    CareersPage careersPage = new CareersPage(driver);

    // Eğer bu assertion başarısız olursa, test metodu çalışmaya devam edecek
    softAssert.assertTrue(careersPage.isPageLoaded(), "Career page sections are not loaded!");
    softAssert.assertTrue(careersPage.isOurLocationsVisible(), "'Our Locations' bölümü görünür değil!");
    softAssert.assertTrue(careersPage.isTeamsVisible(), "'Teams' bölümü görünür değil!");
    softAssert.assertTrue(careersPage.isLifeVisible(), "'Life at Insider' bölümü görünür değil!");

    // Tüm soft assertion'ları kontrol et (fail varsa burada raporlanır)
    softAssert.assertAll();
}

// Test 3: QA iş ilanlarını filtrele
@Test(priority = 3, dependsOnMethods = "testNavigateToCareersPage")
public void testFilterQAJobs() {
    driver.get("https://useinsider.com/careers/quality-assurance/");
    SoftAssert softAssert = new SoftAssert();
    CareersPage careersPage = new CareersPage(driver);
    careersPage.clickSeeAllQaJobs();

    JobListingsPage jobListingsPage = new JobListingsPage(driver);
    jobListingsPage.filterJobs();

    // Eğer bu assertion başarısız olursa, test metodu çalışmaya devam edecek
    softAssert.assertTrue(jobListingsPage.isJobListVisible(), "Job list is not visible!");

    // Tüm soft assertion'ları kontrol et (fail varsa burada raporlanır)
    softAssert.assertAll();
}

// Test 4: Filtrelenmiş iş ilanlarını kontrol et
@Test(priority = 4, dependsOnMethods = "testFilterQAJobs")
public void testValidateJobList() {
    SoftAssert softAssert = new SoftAssert();
    JobListingsPage jobListingsPage = new JobListingsPage(driver);
    // Eğer bu assertion başarısız olursa, test metodu çalışmaya devam edecek
    softAssert.assertTrue(jobListingsPage.areAllJobsCorrect(), "Some jobs do not match the expected criteria!");

    // Tüm soft assertion'ları kontrol et (fail varsa burada raporlanır)
    softAssert.assertAll();
}

// Test 5: "View Role" butonuna tıkla ve Lever Application form sayfasının açıldığını kontrol et
@Test(priority = 5, dependsOnMethods = "testValidateJobList")
public void testViewRoleButtonNavigation(){
    JobListingsPage jobListingsPage = new JobListingsPage(driver);
    String currentWindow = driver.getWindowHandle();  // Mevcut pencereyi sakla
    SoftAssert softAssert = new SoftAssert();

    jobListingsPage.clickViewRoleButton();
    //PageUtils.waitForPageAndAjaxToLoad(driver);
    //Thread.sleep(3000);
// Yeni pencereye geçiş yapan kod kontrol edildi
    boolean isWindowSwitched = false;
    for (String windowHandle : driver.getWindowHandles()) {
        if (!windowHandle.equals(currentWindow)) {
            driver.switchTo().window(windowHandle);
            isWindowSwitched = true;  // Doğru sekme/pencereye geçildi
            break;
        }
    }

    if (isWindowSwitched) {
        // Dinamik bekleme sağla
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Maksimum 10 saniye bekler
        wait.until(ExpectedConditions.urlContains("lever.co"));

        // URL doğrulaması
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        if (!currentUrl.contains("lever.co")) {
            throw new AssertionError("URL beklenildiği gibi yüklenmedi. Mevcut URL: " + currentUrl);
        }
    } else {
        throw new IllegalStateException("Yeni pencereye geçiş yapılamadı.");
    }

    softAssert.assertTrue(isWindowSwitched, "Failed to switch to the new window!");

    // Lever Application form sayfasının açıldığını kontrol et
    // Eğer bu assertion başarısız olursa, test metodu çalışmaya devam edecek
    String currentUrl = driver.getCurrentUrl();
    softAssert.assertNotNull(currentUrl, "Current URL is null!");
    softAssert.assertTrue(currentUrl.contains("lever.co"), "Expected URL to contain 'lever.co', but was: " + currentUrl);

    // Test bittiğinde pencereyi kapat ve eski pencereye dön
    driver.close();
    driver.switchTo().window(currentWindow);
    // Tüm soft assertion'ları kontrol et (fail varsa burada raporlanır)
    softAssert.assertAll();
}
}
