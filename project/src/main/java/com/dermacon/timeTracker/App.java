package com.dermacon.timeTracker;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.TrackingTask;
import com.dermacon.timeTracker.ui.TerminalUI;
import com.dermacon.timeTracker.ui.UserInterface;

import java.util.Scanner;

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

    }

}
