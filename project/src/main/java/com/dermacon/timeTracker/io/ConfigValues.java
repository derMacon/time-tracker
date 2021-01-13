package com.dermacon.timeTracker.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigValues {
    public final static String TIMESTAMP_KEY = "timestampDir";

    public static Properties getPropValues(String propFileName) throws IOException {

        InputStream inputStream = null;

        Properties prop = new Properties();

        try {
            inputStream = ConfigValues.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        return prop;
    }
}
