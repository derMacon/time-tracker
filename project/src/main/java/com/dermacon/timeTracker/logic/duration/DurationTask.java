package com.dermacon.timeTracker.logic.duration;

import java.io.File;
import java.time.Duration;
import java.util.Iterator;

public interface DurationTask extends Iterable<DurationTask> {

    /**
     * Getter for the total duration of the task
     * @return Duration of all involved tasks
     */
    Duration getTotal();

    /**
     * Getter for the tracked tasks which occured today
     * @return Duration of all involved tasks
     */
    Duration getToday();

    /**
     * Getter for the .csv file of the task
     * @return .csv file of the task
     */
    default File getFile() {
        throw new UnsupportedOperationException("add not supported on node");
    }

    /**
     * Adds a given task to the bundle of tasks
     * @param task task to append
     * @return updated bundle
     */
    default DurationTask add(DurationTask task) {
        throw new UnsupportedOperationException("add not supported on leaf");
    }

    /**
     * Getter for a specific index
     * @param i index of the node to return
     * @return Task at the specified index
     */
    default DurationTask get(int i) {
        throw new UnsupportedOperationException("add not supported on leaf");
    }

}
