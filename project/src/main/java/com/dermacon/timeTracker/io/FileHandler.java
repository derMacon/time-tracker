package com.dermacon.timeTracker.io;

import com.dermacon.timeTracker.logic.task.Session;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {

    private static final String CSV_HEADER = "start,finish,total";

    public static List<File> getTrackedFiles() {
        List<File> l = new LinkedList<>();
        l.add(new File("erp.csv"));
        l.add(new File("cg.csv"));
        l.add(new File("pm.csv"));
        l.add(new File("test.csv"));
        return l;
    }

    public static void save(Session session) throws IOException {

        Path p = getCleanFile(session).toPath();
        String s = CSV_HEADER + "\n" + session.toString();

        BufferedWriter writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND);
        writer.write(s);
        writer.close();

        System.out.println("task:     " + session.getFile().getName() + "\n"
                + "appended:\n" + session.toString());
    }

    private static File getCleanFile(Session session) throws IOException {
        File f = session.getFile();
        if (f.exists() && f.isFile()) {
            f.delete();
        }
        f.createNewFile();
        return f;
    }


}
