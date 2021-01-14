package com.dermacon.timeTracker.logic;

import com.dermacon.timeTracker.exception.TimeTrackerException;
import com.dermacon.timeTracker.logic.task.Activity;

import java.util.List;

public class TrackingLogic {

    private Activity selectedActivity;

    public List<Activity> getActivities() throws TimeTrackerException {
        return ActivityLoader.loadActivities();
    }

    public Activity getSelectedActivity() {
        return selectedActivity;
    }

    public void selectActivity(int idx) throws TimeTrackerException {
        selectedActivity = ActivityLoader.loadActivities().get(idx);
    }

    public void createActivity(String name) {
        selectedActivity = ActivityLoader.createActivity(name);
    }

    public void deleteActivity(int idx) {
        System.out.println("delete idx: " + idx);
        // todo
    }

    public boolean isRunning() {
        return selectedActivity != null;
    }

    public boolean activityRunning() {
        return selectedActivity != null
                && selectedActivity.isRunning();
    }

    public void startActivity(Activity selectedActivity) {
        this.selectedActivity = selectedActivity;
        this.selectedActivity.startNewTask();
    }


    public void quit() {
        selectedActivity.stop();

//        try {
//            FileHandler.save(selectedSession);
//        } catch (IOException e) {
//            ui.displayErrorMessage("not possible to save content to file: "
//                    + selectedSession.getFile().toString());
//        }
    }


    public void toggle() {
        if (selectedActivity.isRunning()) {
            selectedActivity.pause();
        } else {
            selectedActivity.resume();
        }
    }

}
