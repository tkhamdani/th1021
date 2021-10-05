package common.properties;

import org.junit.Assert;
import org.junit.Test;

public class AppPropertiesReaderTest {

    @Test
    public void readPropertiesTest() {
        String serviceTypeProperty = AppPropertiesReader.get("service.type", "application.properties");
        String excelDataProperty = AppPropertiesReader.get("data.excel", "application.properties");
        String nonExsitentProperty = AppPropertiesReader.get("THIS PROPERTY DOES NOT EXIST", "application.properties");

        Assert.assertNotNull(serviceTypeProperty);
        Assert.assertNotNull(excelDataProperty);
        Assert.assertNull(nonExsitentProperty);
    }
}
