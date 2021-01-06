package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.logic.TrackingLogic;
import com.dermacon.timeTracker.logic.task.Activity;
import com.dermacon.timeTracker.logic.task.ActivityLoader;

import java.util.List;
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
        this.logic = new TrackingLogic();
    }

    /**
     * Main loop for the program flow.
     */
    public void run() throws TimeTrackerException {
        Activity selectedAct = null;

        do {
            List<Activity> activities = ActivityLoader.loadActivities();
            ui.displayOptions(activities);
            processActivity(selectAcitivity(activities));
        } while (!logic.activityRunning());

    }

    private Activity selectAcitivity(List<Activity> activities) {
        String input = null;
        Scanner sc = new Scanner(System.in);
        Activity out = null;
        do {
            System.out.println("type idx: ");
            input = sc.nextLine();

            if (isInteger(input)) {
                int idx = Integer.parseInt(input);
                // create new activity
                if (idx == 0) {
                    System.out.println("create new");
                    String name = sc.nextLine();
                    out = ActivityLoader.createActivity(name);
                } else if (idx <= activities.size()) {
                    // select existent activity
                    out = activities.get(idx);
                }
            }

        } while (input.equalsIgnoreCase(QUIT_STR) || out == null);

        return out;
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Waits for an user input and delegates it to
     * the appropriate logic methods
     */
    public void processActivity(Activity activity) {
        if (activity == null) {
            return;
        }

        do {
            ui.displayManual();kk
            logic.startActivity(activity);

            Scanner sc = new Scanner(System.in);
            switch (sc.nextLine()) {
                case QUIT_STR:
                    logic.quit();
                    break;
                case PAUSE_STR:
                    logic.toggle();
                    ui.waitForResume();
                    logic.toggle();
                    break;
            }
        } while (logic.activityRunning());

    }

}
