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

        String userInput = "";
        Scanner s = new Scanner(System.in);

        do {
            logic.showMenu();
            logic.selectTask(ui.selectTask());
//            userSelect = ui.waitForUserInteraction();
//            logic.handleInteraction(userSelect);
            userInteractionCycle();

            System.out.print(REPEAT);
            userInput = s.nextLine();

        } while (userInput.equalsIgnoreCase("y"));
    }

    private void userInteractionCycle() {
        InteractionMode userSelect;
        do {
            userSelect = ui.waitForUserInteraction();
            logic.handleInteraction(userSelect);
            logic.showMenu();
        } while (userSelect == InteractionMode.PAUSE);
    }

}
