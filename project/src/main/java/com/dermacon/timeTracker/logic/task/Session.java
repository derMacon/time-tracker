package com.dermacon.timeTracker.logic.task;

import javax.sound.midi.Track;
import java.io.File;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class Session {

    private File file;

    private Stack<TrackingTask> tasks = new Stack<>();

    public Session(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean isRunning() {
        return !tasks.empty() && tasks.peek().isRunning();
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
        return
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (TrackingTask task : tasks) {
            out.insert(0, task.toString());
        }
        return out.toString();
    }

}
