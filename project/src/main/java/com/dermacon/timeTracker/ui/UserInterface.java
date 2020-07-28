package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.task.Session;
import com.dermacon.timeTracker.logic.duration.DurationTask;

import java.util.List;
import java.util.Map;

public interface UserInterface {

    void displayInfo(DurationTask bundle);

    void displayOptions(Map<String, List<String>> table);

    /**
     * Setter for the task
     */
    int selectTask();

    /**
     * User can change the ending time of the task
     */
    int editEndingTime(String task_name);

    InteractionMode waitForUserInteraction();

    void waitForResume();

    void startTimerDisplay(Session task);

    void endTimerDisplay(Session task);

}
