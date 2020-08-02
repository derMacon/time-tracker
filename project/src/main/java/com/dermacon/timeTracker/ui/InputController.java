package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.TrackingLogic;

import java.util.Scanner;

/**
 * Controller class for the ui.
 * Serves as an interface between the logic and the ui.
 */
public class InputController {

    private static final String REPEAT = "continue time tracking [y/n]: ";
    private static final String QUIT_STR = "q";
    private static final String EDIT_STR = "e";
    private static final String PAUSE_STR = "p";

    private final UserInterface ui;
    private TrackingLogic logic;


    public InputController(UserInterface ui) {
        this.ui = ui;
        this.logic = new TrackingLogic(ui);
    }


    /**
     * Main loop for the program flow.
     */
    public void run() {

        Scanner s = new Scanner(System.in);
        String userInput;

        do {
            logic.selectTask();
            waitForUserInteraction();

            System.out.print(REPEAT);
            userInput = s.nextLine();

        } while (userInput.equalsIgnoreCase("y"));

    }


    /**
     * Loop for handling user interactions
     */
    private void waitForUserInteraction() {
        do {
            ui.displayManual();
            handleUserInteraction();
            logic.showMenu();
        } while (logic.isRunning());
    }


    /**
     * Waits for an user input and delegates it to
     * the appropriate logic methods
     */
    public void handleUserInteraction() {
        Scanner s = new Scanner(System.in);
        String userInteraction;
        do {
            userInteraction = s.next().toLowerCase();
        } while (!userInteraction.equals(QUIT_STR)
                && !userInteraction.equals(EDIT_STR)
                && !userInteraction.equals(PAUSE_STR));

        switch (userInteraction) {
            case PAUSE_STR:
                logic.handlePause();
                break;
            case EDIT_STR:
                logic.editEndingTime();
                logic.quit();
                break;
            case QUIT_STR:
                logic.quit();
                break;
        }
    }

}
