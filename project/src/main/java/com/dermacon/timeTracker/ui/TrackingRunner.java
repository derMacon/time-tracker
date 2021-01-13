package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.logic.TrackingLogic;

public class TrackingRunner {

    public void run() throws TimeTrackerException {
        TrackingLogic logic = new TrackingLogic();
        UserInterface ui = new TerminalUI();

        MenuController menuController = new MenuController(ui, logic);
        TrackingController trackingController = new TrackingController(ui, logic);

        menuController.run();
        trackingController.run();
    }
}
