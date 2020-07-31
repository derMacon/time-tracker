package com.dermacon.timeTracker.logic.task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;

// todo maybe rethink project structure...
import static com.dermacon.timeTracker.logic.duration.DurationFactory.createFormattedStr;

public class Session {

    /**
     * Hour offset for midnight. A new day for the tracking task does not start at
     * 0 o'clock but at 8 am. Makes it possible to track a task from e.g. 11 pm to 3 am.
     */
    private static final int NEW_DAY_HOUR = 8;

    private File file;

    private Stack<TrackingTask> tasks = new Stack<>();

    public Session(File file) {
        this.file = file;
        this.tasks.push(new TrackingTask());
    }

    public Stack<TrackingTask> getTasks() {
        return tasks;
    }

    public File getFile() {
        return file;
    }

    public void addTrackingTask(TrackingTask task) {
        this.tasks.push(task);
    }


    public boolean isRunning() {
        return !tasks.empty() && tasks.peek().isRunning();
    }

    public void pause() {
        if (isRunning()) {
            tasks.peek().stopTask();
        } else {
            // todo throw Exception
        }
    }

    public void resume() {
        if (!isRunning()) {
            tasks.push(new TrackingTask());
        } else {
            // todo throw Exception
        }
    }

    public void addMinutes(int min) {
        if (!tasks.isEmpty()) {
            tasks.peek().addMinutes(min);
        }
    }

    public String displayPassedTime() {
        Duration total = Duration.ZERO;
        for (TrackingTask curr : tasks) {
            total = total.plus(curr.getDuration());
        }
        return createFormattedStr(total);
    }


    /**
     * todo
     * @param task
     * @return
     */
    private static boolean isSameDay(TrackingTask task) {
        return task != null && isSameDay(task.getStart())
                && (task.isRunning() || isSameDay(task.getEnd()));
    }

    /**
     * Checks if a given timestamp occured on the same day as the current date.
     * Light modification: A new day does not start at 0 am but at NEW_DAY_HOUR am. This makes
     * it possible to track tasks in the middle of the night without any interruptions.
     * @param timeStamp timestamp to check
     * @return true if the timestamp occurred on the current day
     */
    private static boolean isSameDay(LocalDateTime timeStamp) {
        timeStamp = timeStamp.minusHours(NEW_DAY_HOUR);
        LocalDateTime fake_midnight = LocalDateTime.now().minusHours(NEW_DAY_HOUR);

        Duration diff = Duration.between(timeStamp, fake_midnight);

        return fake_midnight.getDayOfYear() == timeStamp.getDayOfYear()
                && !diff.isNegative() && diff.getSeconds() <= 24 * 60 * 60;
    }


    public Duration getTotal() {
        Duration out = Duration.ZERO;
        for (TrackingTask task : this.tasks) {
            out = out.plus(task.getDuration());
        }
        return out;
    }


    public Duration getToday() {
        Duration out = Duration.ZERO;

        for (TrackingTask task : this.tasks) {

            if (isSameDay(task)) {
                out = out.plus(task.getDuration());
            }

        }

        return out;
    }


    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (TrackingTask task : tasks) {
            out.insert(0, task.toString() + "\n");
        }
        return out.toString();
    }

}
