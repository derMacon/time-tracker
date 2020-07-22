package com.dermacon.timeTracker;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class TerminalUI implements UserInterface {

    private static final String INTRO = "time-tracker v1.0\n";

    private static final int FST_COL_WIDTH = 5;
    private static final int SND_COL_WIDTH = 10;
    private static final int THRD_COL_WIDTH = 10;
    private static final String LINE_FORMAT = "|%1$-" + FST_COL_WIDTH +
            "s|%2$-10s|%3$-20s|\n";
    private static final int DISPLAY_INTERVAL = 1000;

    private Timer t = new Timer(true);

    @Override
    public TrackingTask selectTask() {
        StringBuilder options = new StringBuilder(INTRO);
        options.append("select tracking task:\n");

        options.append(System.out.format(LINE_FORMAT,
                StringUtils.center("idx",10),
                StringUtils.center("name",10),
                StringUtils.center("duration",20)));


        List<File> trackedFiles = FileHandler.getTrackedFiles();
        int option_cnt = trackedFiles.size();

        for (int i = 0; i < option_cnt; i++) {
            options.append(System.out.format(LINE_FORMAT,
                    StringUtils.center(i + "",10),
                    StringUtils.center(trackedFiles.get(i).getName(),10),
                    StringUtils.center("-",20)));
        }
        options.append((option_cnt + 1) + ". new tracking task\n");
        System.out.println(options);

        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            userInput = s.nextLine();
            // todo check if valid idx
        } while (!userInput.matches("\\d+"));

        return new TrackingTask(trackedFiles.get(Integer.parseInt(userInput) - 1));
    }


    @Override
    public void waitForUserAbortion(TrackingTask task) {
        Scanner s = new Scanner(System.in);
        String userInteraction;
        do {
            userInteraction = s.next();
        } while (!userInteraction.equals("q") && !userInteraction.equals("q!"));

        task.stopTask();

        // user wants to edit ending time
        if (userInteraction.equals("q")) {
            editEndingTime(task);
        }

    }


    @Override
    public void editEndingTime(TrackingTask task) {
        System.out.println("Edit current task: " + task.getFile().getName() + "\n"
                + "type the number of minutes you want to subtract from the " +
                "end time");
        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            userInput = s.nextLine();
        } while (!userInput.matches("\\d+"));

        task.subtractMinutes(Integer.parseInt(userInput));
    }


    @Override
    public void startTimerDisplay(TrackingTask task) {
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (task.isRunning()) {
                    System.out.print("[" + task.displayPassedTime() + "] | " +
                            "user input>\r");
                } else {
                    System.out.print("task not running\r");
                }
            }
        }, 0, DISPLAY_INTERVAL);
    }


    @Override
    public void endTimerDisplay(TrackingTask task) {
        t.cancel();
    }
}
