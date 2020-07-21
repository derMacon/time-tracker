package com.dermacon.timeTracker;

import java.io.File;

public interface UserInterface {

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
