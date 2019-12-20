package com.dermacon.timeTracker;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        System.out.println(CSVReader.readCSV("test.csv"));

//        System.out.println(System.getProperty("user.dir"));
//
//        System.out.println();
//        System.out.println("file exists: " + new File("properties.config").exists());

    }
}
