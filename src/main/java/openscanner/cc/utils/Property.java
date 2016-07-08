package openscanner.cc.utils;

import io.vertx.core.json.JsonObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

    public Properties getProperties(String path) {
        Properties properties = null;
        try {
            InputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public JsonObject ReadPropertiesToJsonObject(String path) {
        Properties properties = getProperties(path);
        JsonObject json = new JsonObject();
        for (String key : properties.stringPropertyNames()) {
            json.put(key, properties.getProperty(key));
        }
        return json;
    }
}