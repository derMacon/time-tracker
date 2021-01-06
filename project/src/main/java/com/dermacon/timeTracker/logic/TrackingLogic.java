package com.dermacon.timeTracker.logic;

import com.dermacon.timeTracker.logic.task.Activity;
import com.dermacon.timeTracker.logic.task.ActivityLoader;

public class TrackingLogic {

    private Activity selectedActivity;

    public boolean activityRunning() {
        return selectedActivity != null
                && selectedActivity.isRunning();
    }

    public void startActivity(Activity selectedActivity) {
        this.selectedActivity = selectedActivity;
        this.selectedActivity.startNewTask();
    }


    //
//    public void showGeneralInfo() {
//        List<Session> sessions = SessionLoader.createSessionLst();
//        Duration total = Session.getDurationSum_total(trackedSessions);
//        Duration today = Session.getDurationSum_today(trackedSessions);
//
//        ui.displayTotalDuration(total);
//        ui.displayTodayDuration(today);
//    }
//
//
//    private Duration getTodayDuration() {
//        Duration out = Duration.ZERO;
//        for (Session session : trackedSessions) {
//            out = out.plus(session.getTodayDuration());
//        }
//        return out;
//    }
//
//
//    public void showMenu() {
//        ui.displayOptions(trackedSessions);
//    }
//
//
//    public void select(int userSelection) {
//        selectedSession = trackedSessions.get(userSelection - 1);
//        selectedSession.startNewTask();
//        ui.startTimerDisplay(selectedSession);
//    }
//
//    public void editEndingTime() {
//        int offset_minutes = ui.editEndingTime(selectedSession.getFile().getName());
//        selectedSession.addMinutes(offset_minutes * -1);
//    }
//
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
