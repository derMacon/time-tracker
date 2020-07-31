package com.dermacon.timeTracker.logic.task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

public class Session {

    /**
     * Hour offset for midnight. A new day for the tracking task does not start at
     * 0 o'clock but at 8 am. Makes it possible to track a task from e.g. 11 pm to 3 am.
     */
    private static final int NEW_DAY_HOUR = 8;

    private final File file;

    private Stack<TrackingTask> tasks = new Stack<>();

    public Session(File file) {
        this.file = file;
        this.tasks.push(new TrackingTask());
    }

    public File getFile() {
        return file;
    }

    public void startNewTask() {
        addTrackingTask(new TrackingTask());
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


    public Duration getTotalDuration() {
        return Session.sumUpTasks(tasks, e -> true);
    }


    public Duration getTodayDuration() {
        return Session.sumUpTasks(tasks, Session::isSameDay);
    }


    public static Duration getDurationSum_total(List<Session> sessions) {
        return sumUpSessions(sessions, e -> true);
    }

    public static Duration getDurationSum_today(List<Session> sessions) {
        return sumUpSessions(sessions, Session::isSameDay);
    }

    private static Duration sumUpSessions(Collection<Session> sessions,
                                       Predicate<TrackingTask> filter) {
        Duration out = Duration.ZERO;
        for (Session session : sessions) {
            out = out.plus(sumUpTasks(session.tasks, filter));
        }
        return out;
    }

    private static Duration sumUpTasks(Collection<TrackingTask> tasks,
                                       Predicate<TrackingTask> filter) {
        Duration out = Duration.ZERO;
        for (TrackingTask task : tasks) {
            if (filter.test(task)) {
                out = out.plus(task.getDuration());
            }
        }
        return out;
    }


    // ---- formatting ----


    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (TrackingTask task : tasks) {
            out.insert(0, task.toString() + "\n");
        }
        return out.toString();
    }

}
