package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.task.Session;

import java.time.Duration;
import java.util.List;

/**
 * Interface for the user to interact with
 */
public interface UserInterface {

    /**
     * Displays the usage in the terminal
     */
    void displayManual();

    /**
     * Displays the summed up Duration from all sessions in total
     * @param total Duration from all sessions in total
     */
    void displayTotalDuration(Duration total);

    /**
     * Displays the summed up Duration from all sessions that were tracked today
     * @param today Duration from all sessions that were tracked today
     */
    void displayTodayDuration(Duration today);

    /**
     * Displays all tracked Sessions in a table view
     * @param trackedSessions Sessions to display
     */
    void displayOptions(List<Session> trackedSessions);

    /**
     * Setter for the task
     */
    int selectTask();

    /**
     * User can change the ending time of the task
     */
    int editEndingTime(String task_name);

    /**
     * Wait till the user resumes the Session
     */
    void waitForResume();

    /**
     * Starts timer
     * @param task task to display
     */
    void startTimerDisplay(Session task);

    /**
     * Ends timer
     * @param task task to display
     */
    void endTimerDisplay(Session task);

    /**
     * Displays an error message on the gui
     * @param message message to display
     */
    void displayErrorMessage(String message);

}
