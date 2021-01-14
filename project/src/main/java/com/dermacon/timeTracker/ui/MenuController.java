package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.logic.commands.MenuToken;
import com.dermacon.timeTracker.logic.TrackingLogic;
import com.dermacon.timeTracker.logic.task.Activity;

import java.util.List;
import java.util.regex.Matcher;

import static com.dermacon.timeTracker.exception.ErrorCode.INVALID_MENU_INPUT;

/**
 * Controller class for the ui.
 * Serves as an interface between the logic and the ui.
 */
public class MenuController {

    private final UserInterface ui;
    private final TrackingLogic logic;

    public MenuController(UserInterface ui, TrackingLogic logic) {
        this.ui = ui;
        this.logic = logic;
    }

    /**
     * Main loop for the program flow.
     */
    public void run() throws TimeTrackerException {
        List<Activity> activities = logic.getActivities();
        ui.displayActivities(activities);
        ui.displayMenuOptions();
        ui.displayMenuCursor();
        parseMenuInput();
    }

    private void parseMenuInput() throws TimeTrackerException {
        String input = ui.menuInput();
        boolean foundMatcher = false;

        Matcher m = MenuToken.SELECT.getMatcher(input);
        if (m.matches()) {
            int idx = Integer.parseInt(m.group(1));
            logic.selectActivity(idx);
            foundMatcher = true;
        }

        m = MenuToken.DELETE.getMatcher(input);
        if (!foundMatcher && m.matches()) {
            int idx = Integer.parseInt(m.group(1));
            logic.deleteActivity(idx);
            foundMatcher = true;
        }

        m = MenuToken.CREATE.getMatcher(input);
        if (!foundMatcher && m.matches()) {
            logic.createActivity(m.group(1));
            foundMatcher = true;
        }

        if (!foundMatcher) {
            ui.displayErrorMessage(INVALID_MENU_INPUT);
            ui.displayMenuCursor();
            parseMenuInput();
        }
    }

}
