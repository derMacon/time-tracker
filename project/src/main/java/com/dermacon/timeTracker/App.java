package com.dermacon.timeTracker;

import org.apache.commons.io.IOUtils;

import javax.sound.midi.Track;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 */
public class App {

    private static final String REPEAT = "continue time tracking [y/n]: ";

    public static void main(String[] args) {

        String userInput;
        Scanner s = new Scanner(System.in);

        do {
            UserInterface ui = new TerminalUI();
            ui.displayOptions();
            TrackingTask task = ui.selectTask();
            ui.startTimerDisplay(task);
            ui.waitForUserAbortion(task);
            ui.endTimerDisplay(task);
            FileHandler.save(task);

            System.out.print(REPEAT);
            userInput = s.nextLine();
        } while (userInput.equalsIgnoreCase("y"));

//        System.out.println(CSVReader.readCSV("test.csv"));
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println();
//        System.out.println("file exists: " + new File("properties.config").exists());
    }


    private static void save() {
        FileHandler fileHandler = new FileHandler();


    }


}
