package com.dermacon.timeTracker;

import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.ui.MenuController;
import com.dermacon.timeTracker.ui.TerminalUI;
import com.dermacon.timeTracker.ui.TrackingRunner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws TimeTrackerException {
//        try {
//            new MenuController(new TerminalUI()).run();
//        } catch (TimeTrackerException e) {
//            System.err.println("error: " + e.getMessage());
//        }

        try {
            new TrackingRunner().run();
        } catch (TimeTrackerException e) {
            System.err.println("error: " + e.getMessage());
        }

    }

}
