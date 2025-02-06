package uiTest.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;

    /**
     * Bu metod, test sınıfı başlamadan bir kez çalışır ve
     * "browser" parametresine göre Chrome veya Firefox için uygun tarayıcı oturumunu oluşturur.
     */
    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            // Chrome için seçenekler
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            // Bildirimleri izin ver (1 = Allow, 2 = Block, 0 = Default)
            prefs.put("profile.default_content_setting_values.notifications", 1);
            options.setExperimentalOption("prefs", prefs);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Firefox için profil ayarları
            FirefoxProfile profile = new FirefoxProfile();
            // Firefox'ta web bildirimlerini etkinleştir (true = enabled)
            profile.setPreference("dom.webnotifications.enabled", true);
            FirefoxOptions options = new FirefoxOptions();
            options.setProfile(profile);
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * Bu metod, test sınıfı tamamlandığında bir kez çalışır ve
     * oluşturulan tarayıcı oturumunu kapatır.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
