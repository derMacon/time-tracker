package com.dermacon.timeTracker.logic.task;

import java.io.File;
import java.time.Duration;
import java.util.Stack;

// todo maybe rethink project structure...
import static com.dermacon.timeTracker.logic.duration.DurationFactory.createFormattedStr;

public class Session {

    private File file;

    private Stack<TrackingTask> tasks = new Stack<>();

    public Session(File file) {
        this.file = file;
        this.tasks.push(new TrackingTask());
    }

    public File getFile() {
        return file;
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (TrackingTask task : tasks) {
            out.insert(0, task.toString() + "\n");
        }
        return out.toString();
    }

}
