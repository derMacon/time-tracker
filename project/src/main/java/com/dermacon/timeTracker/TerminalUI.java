package com.dermacon.timeTracker;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class TerminalUI implements UserInterface {

    private static final String INTRO = "time-tracker v1.0\n";
    private static final String MANUAL = "usage:\n" +
            "  - p: pause (wip)\n" +
            "  - i: insert pause (wip)\n" +
            "  - q: quit without editing\n" +
            "  - e: quit with editing\n";

    private static final int FST_COL_WIDTH = 5;
    private static final int SND_COL_WIDTH = 20;
    private static final int THRD_COL_WIDTH = 20;

    private static final String LINE_FORMAT = "|%1$-" + FST_COL_WIDTH + "s"
            + "|%2$-" + SND_COL_WIDTH + "s"
            + "|%3$-" + THRD_COL_WIDTH + "s|\n";
    private static final int DISPLAY_INTERVAL = 1000;

    private Timer t = new Timer(true);

    @Override
    public TrackingTask selectTask() {
        StringBuilder options = new StringBuilder(INTRO + "\n");

        options.append(String.format(LINE_FORMAT,
                StringUtils.center("idx",FST_COL_WIDTH),
                StringUtils.center("name",SND_COL_WIDTH),
                StringUtils.center("duration",THRD_COL_WIDTH)));

        options.append(getHoriLine());

        List<File> trackedFiles = FileHandler.getTrackedFiles();
        int option_cnt = trackedFiles.size();

        for (int i = 0; i < option_cnt; i++) {
            options.append(String.format(LINE_FORMAT,
                    StringUtils.center((1 + i) + "",FST_COL_WIDTH),
                    StringUtils.center(trackedFiles.get(i).getName(),SND_COL_WIDTH),
                    StringUtils.center("-",THRD_COL_WIDTH)));
        }

        options.append(String.format(LINE_FORMAT,
                StringUtils.center((option_cnt + 1) + "",FST_COL_WIDTH),
                StringUtils.center("new task",SND_COL_WIDTH),
                StringUtils.center("-",THRD_COL_WIDTH)));

        options.append("\nuser input: ");
        System.out.print(options);

        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            userInput = s.nextLine();
            // todo check if valid idx
        } while (!userInput.matches("\\d+"));

        return new TrackingTask(trackedFiles.get(Integer.parseInt(userInput) - 1));
    }


    private String getHoriLine() {
        StringBuilder out = new StringBuilder("+");
        int line_width = FST_COL_WIDTH + SND_COL_WIDTH + THRD_COL_WIDTH;
        for (int i = 1; i <= line_width; i++) {
            if (i == FST_COL_WIDTH
                    || i == FST_COL_WIDTH + SND_COL_WIDTH
                    || i == FST_COL_WIDTH + SND_COL_WIDTH + THRD_COL_WIDTH
            ) {
                out.append("-+");
            } else {
                out.append("-");
            }
        }
        out.append("\n");
        return out.toString();
    }



    @Override
    public void waitForUserAbortion(TrackingTask task) {

        System.out.println("\n" + MANUAL);

        Scanner s = new Scanner(System.in);
        String userInteraction;
        do {
            userInteraction = s.next();
        } while (!userInteraction.equals("q") && !userInteraction.equals("e"));

        task.stopTask();

        // user wants to edit ending time
        if (userInteraction.equals("e")) {
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
                    System.out.print("user input [" + task.displayPassedTime()
                            + "] > \r");
                }
            }
        }, 10, DISPLAY_INTERVAL);
    }


    @Override
    public void endTimerDisplay(TrackingTask task) {
        t.cancel();
    }
}
