package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.TrackingTask;

import java.util.Scanner;

public class InputController {

    private static final String REPEAT = "continue time tracking [y/n]: ";

    private final UserInterface ui;


    public InputController(UserInterface ui) {
        this.ui = ui;
    }

    public void start() {

        String userInput;
        Scanner s = new Scanner(System.in);

        do {
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
