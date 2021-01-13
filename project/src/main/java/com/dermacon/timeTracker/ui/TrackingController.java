package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.TrackingLogic;
import com.dermacon.timeTracker.logic.task.Activity;

public class TrackingController {

    private final UserInterface ui;
    private final TrackingLogic logic;

    public TrackingController(UserInterface ui, TrackingLogic logic) {
        this.ui = ui;
        this.logic = logic;
    }

    public void run() {
        do {
            Activity currActivity = logic.getSelectedActivity();
            ui.displayCurrActivity(currActivity);
            ui.displayManual();
        } while (logic.activityRunning());


    }


}
