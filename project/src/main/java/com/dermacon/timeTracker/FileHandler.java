package com.dermacon.timeTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {

    public static List<File> getTrackedFiles() {
        List<File> l = new LinkedList<>();
        l.add(new File("erp.csv"));
        return l;
    }

    public static void save(TrackingTask task) {
        File f = task.getFile();

        Path p = f.toPath();
        String s = System.lineSeparator() + task.toString();

        try (BufferedWriter writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND)) {
            writer.write(s);
        } catch (IOException ioe) {
            System.err.format("IOException: %s%n", ioe);
        }

        System.out.println("task:     " + task.getFile().getName() + "\n"
                + "appended: " + task.toString());
    }


}
