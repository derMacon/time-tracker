package com.dermacon.timeTracker;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TerminalUI implements UserInterface {

    private static final int DISPLAY_INTERVAL = 10000;

    private Timer t = new Timer(true);

    @Override
    public TrackingTask selectTask() {
        List<File> trackedFiles = FileHandler.getTrackedFiles();
        int option_cnt = trackedFiles.size();

        StringBuilder options = new StringBuilder("select tracking task:\n");
        for (int i = 0; i < option_cnt; i++) {
            options.append((i + 1) + ". " + trackedFiles.get(i) + "\n");
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
                    System.out.println(task);
                }
            }
        }, 0, DISPLAY_INTERVAL);
    }


    @Override
    public void endTimerDisplay(TrackingTask task) {
        t.cancel();
    }
}
