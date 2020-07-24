package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.TrackingTask;

public interface UserInterface {

    void displayOptions();

    /**
     * Setter for the task
     */
    TrackingTask selectTask();

    /**
     * User can change the ending time of the task
     * @param task task to modify
     */
    void editEndingTime(TrackingTask task);

    void waitForUserAbortion(TrackingTask task);


    void startTimerDisplay(TrackingTask task);

    void endTimerDisplay(TrackingTask task);

}
