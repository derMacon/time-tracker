package com.dermacon.timeTracker.logic;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.task.Session;
import com.dermacon.timeTracker.logic.task.SessionFactory;
import com.dermacon.timeTracker.ui.UserInterface;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.dermacon.timeTracker.logic.task.Session.formatDuration;

public class TrackingLogic {

//    private static final String INFO_FORMAT = "total: %s\ntoday: %s\n\n";

    private static final String IDX_KEY = "idx";
    private static final String NAME_KEY = "name";
    private static final String TODAY_KEY = "today";
    private static final String TOTAL_KEY = "total";

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
        Map<String, List<Duration>> table = convertSessionsToMap(trackedSessions);
        ui.displayOptions(table);
    }

    private static Map<String, List<Duration>> convertSessionsToMap(List<Session> sessions) {
        // important, keep keys in order they were inserted (LinkedHashMap)
        Map<String, List<Duration>> table = new LinkedHashMap<>();

        List<String> idx = new LinkedList<>();
        List<String> name = new LinkedList<>();
        List<Duration> today = new LinkedList<>();
        List<Duration> total = new LinkedList<>();
        int i = 1;

        for (Session currSession : sessions) {
            idx.add(String.valueOf(i++));
            name.add(currSession.getFile().getName());
            today.add(currSession.getTotalDuration());
            total.add(currSession.getTodayDuration());
        }

        table.put(IDX_KEY, idx);
        table.put(NAME_KEY, name);
        table.put(TODAY_KEY, today);
        table.put(TOTAL_KEY, total);
        return table;
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
        FileHandler.save(selectedSession);
    }

    public void handlePause() {
        selectedSession.pause();
        ui.waitForResume();
        selectedSession.resume();
    }

}
