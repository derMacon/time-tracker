package com.dermacon.timeTracker.io;

import com.dermacon.timeTracker.logic.task.Activity;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {

    private static final String CSV_HEADER = "start,finish,total";
    private static final String CONFIG_FILE = "config.properties";
    private static final String TIMESTAMP_DIR = "timestamps";
    private static final String CSV_EXTENSION = "csv";


    public static List<File> getTrackedFiles() {
        File dir = new File(TIMESTAMP_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
            return new LinkedList<>();
        }

        return Arrays.stream(dir.listFiles())
                .filter(f -> getFileExtension(f).equalsIgnoreCase(CSV_EXTENSION))
                .collect(Collectors.toList());
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }

    public static void save(Activity activity) throws IOException {

        Path p = getCleanFile(activity).toPath();
        String s = CSV_HEADER + "\n" + activity.toString();

        BufferedWriter writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND);
        writer.write(s);
        writer.close();

        System.out.println("task:     " + activity.getFile().getName() + "\n"
                + "appended:\n" + activity.toString());
    }

    private static File getCleanFile(Activity activity) throws IOException {
        File f = activity.getFile();
        if (f.exists() && f.isFile()) {
            f.delete();
        }
        f.createNewFile();
        return f;
    }


    public static File createCsv(String name, String content) {
        File file = new File(TIMESTAMP_DIR + "/" + name + "." + CSV_EXTENSION);
        try {
            FileUtils.writeStringToFile(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
