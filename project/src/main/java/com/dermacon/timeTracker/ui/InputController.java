package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.TrackingLogic;

import java.util.Scanner;

public class InputController {

    private static final String REPEAT = "continue time tracking [y/n]: ";

    private final UserInterface ui;
    private TrackingLogic logic;


    public InputController(UserInterface ui) {
        this.ui = ui;
        this.logic = new TrackingLogic(ui);
    }

    public void start() {

        String userInput;
        Scanner s = new Scanner(System.in);

        do {
            logic.showMenu();
            logic.selectTask(ui.selectTask());
            logic.handleQuitting(ui.waitForUserAbortion());

            System.out.print(REPEAT);
            userInput = s.nextLine();
        } while (userInput.equalsIgnoreCase("y"));
    }

}
