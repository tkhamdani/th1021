package common.properties;

import org.apache.commons.collections.MapUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppPropertiesReader {

    private AppPropertiesReader() {}

    public static String get(String propertyName, String filePath) {
        Map<String, String> properties = readProperties(filePath);
        if(MapUtils.isEmpty(properties)) {
            return null;
        } else {
            return properties.get(propertyName);
        }
    }

    public static Map<String, String> readProperties(String path) {
        return getPropertiesFromPropertyFile(path);
    }

    private static Map<String, String> getPropertiesFromPropertyFile(String relativePath) {
        Map<String, String> properties = new HashMap<String, String>();
        java.util.Properties prop = new java.util.Properties();
        try {
            InputStream input = AppPropertiesReader.class.getClassLoader().getResourceAsStream(relativePath);
            prop.load(input);
            Set<String> propertyNames = prop.stringPropertyNames();
            for (String Property : propertyNames) {
                properties.put(Property, prop.getProperty(Property));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }
}
