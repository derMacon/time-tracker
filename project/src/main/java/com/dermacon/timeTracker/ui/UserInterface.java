package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.task.Session;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface UserInterface {


    void displayManual();

    void displayTotalDuration(Duration total);

    void displayTodayDuration(Duration today);

    void displayOptions(Map<String, List<String>> table);

    /**
     * Setter for the task
     */
    int selectTask();

    /**
     * User can change the ending time of the task
     */
    int editEndingTime(String task_name);

    void waitForResume();

    void startTimerDisplay(Session task);

    void endTimerDisplay(Session task);

}
