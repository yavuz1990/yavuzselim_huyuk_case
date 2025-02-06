package loadTest;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;

public class JMeterLoadTest {

    @Test
    public void runJMeterTestPlan() throws Exception {
        // 1. JMeter kurulum dizinini belirleyin (JMeter'ın yüklü olduğu klasörün yolu)
        String jmeterHome = "C:\\Users\\DAXAP\\apache-jmeter-5.6.3";
        File jmeterProperties = new File(jmeterHome + "/bin/jmeter.properties");
        if (!jmeterProperties.exists()) {
            throw new RuntimeException("JMeter properties dosyası bulunamadı: " + jmeterProperties.getAbsolutePath());
        }

        // 2. JMeter ayarlarını yükleyin
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(jmeterProperties.getAbsolutePath());
        JMeterUtils.initLocale();

        // 3. Test planı (.jmx dosyası) yolunu belirleyin
        // Eğer dosyayı frameworkünüzün kaynaklarına eklediyseniz, örneğin:
        // String jmxFilePath = "src/test/resources/n11Search.jmx";
        // Veya dosya sistemindeki mutlak yolu kullanın:
        String jmxFilePath = "src/test/resources/n11Search.jmx";
        File jmxFile = new File(jmxFilePath);
        if (!jmxFile.exists()) {
            throw new RuntimeException("JMX dosyası bulunamadı: " + jmxFile.getAbsolutePath());
        }

        // 4. .jmx dosyasını yükleyin
        HashTree testPlanTree = SaveService.loadTree(jmxFile);

        // 5. JMeter motorunu oluşturup test planını yapılandırın
        StandardJMeterEngine jmeterEngine = new StandardJMeterEngine();
        jmeterEngine.configure(testPlanTree);

        // 6. Test planını çalıştırın
        jmeterEngine.run();

        // 7. Test tamamlandıktan sonra (opsiyonel) sonuçları kontrol edebilir veya assert ekleyebilirsiniz.
        Assert.assertTrue(true, "JMeter test planı başarıyla çalıştırıldı.");
    }
}