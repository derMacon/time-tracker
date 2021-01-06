package com.dermacon.timeTracker;

import com.dermacon.timeTracker.exception.InvalidCSVFormattingException;
import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.ui.InputController;
import com.dermacon.timeTracker.ui.TerminalUI;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        try {
            new InputController(new TerminalUI()).run();
        } catch (TimeTrackerException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

}
