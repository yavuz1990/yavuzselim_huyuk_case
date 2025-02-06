package uiTest.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // BaseTest sınıfından driver'ı al
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).driver;

        if (driver != null) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            try {
                String testName = result.getName(); // Hatalı testin adı
                File destFile = new File("screenshots/" + testName + ".png");
                Files.copy(srcFile.toPath(), destFile.toPath());
                System.out.println("Ekran görüntüsü kaydedildi: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}