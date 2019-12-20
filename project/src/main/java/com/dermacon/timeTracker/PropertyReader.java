package com.dermacon.timeTracker;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

public class PropertyReader {

    public String getPropValue() throws IOException {

        String result = "";

        Properties prop = new Properties();
        String propFileName = "/config.properties";

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("prop name: " + propFileName);
        }

        Date time = new Date(System.currentTimeMillis());

        return result;
    }


    private String readFile(String input) throws IOException {

        InputStream inputStream = App.class.getResourceAsStream(input);
        StringWriter writer = new StringWriter();
        String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(inputStream, writer, encoding);
        return writer.toString();
    }




}
