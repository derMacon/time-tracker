package com.dermacon.timeTracker.logic;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.task.Session;
import com.dermacon.timeTracker.logic.task.SessionFactory;
import com.dermacon.timeTracker.ui.UserInterface;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TrackingLogic {

    private final UserInterface ui;

    private List<Session> trackedSessions;

    private Session selectedSession;


    public TrackingLogic(UserInterface ui) {
        this.ui = ui;

        try {
            // todo handle exception properly
            this.trackedSessions = SessionFactory.createSessionLst();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return selectedSession.isRunning();
    }

    public void showGeneralInfo() {
        Duration total = Session.getDurationSum_total(trackedSessions);
        Duration today = Session.getDurationSum_today(trackedSessions);

        ui.displayTotalDuration(total);
        ui.displayTodayDuration(today);
    }


    private Duration getTodayDuration() {
        Duration out = Duration.ZERO;
        for (Session session : trackedSessions) {
            out = out.plus(session.getTodayDuration());
        }
        return out;
    }


    public void showMenu() {
        ui.displayOptions(trackedSessions);
    }

    public void selectTask() {
        showGeneralInfo();
        showMenu();
        select(ui.selectTask());
    }

    public void select(int userSelection) {
        selectedSession = trackedSessions.get(userSelection - 1);
        selectedSession.startNewTask();
        ui.startTimerDisplay(selectedSession);
    }

    public void editEndingTime() {
        int offset_minutes = ui.editEndingTime(selectedSession.getFile().getName());
        selectedSession.addMinutes(offset_minutes * -1);
    }

    public void quit() {
        ui.endTimerDisplay(selectedSession);
        selectedSession.stop();

        try {
            FileHandler.save(selectedSession);
        } catch (IOException e) {
            ui.displayErrorMessage("not possible to save content to file: "
                    + selectedSession.getFile().toString());
        }
    }

    public void handlePause() {
        selectedSession.pause();
        ui.waitForResume();
        selectedSession.resume();
    }

}
