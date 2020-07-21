package com.dermacon.timeTracker;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TrackingTask {

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd" +
            "-MM" +
            "-yyyy HH:mm:ss");

    private LocalDateTime start = LocalDateTime.now();
    private LocalDateTime end;

    private File file;

    public TrackingTask(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean isRunning() {
        return end == null;
    }

    public void stopTask() {
        this.end = LocalDateTime.now();
    }

    public void subtractMinutes(int min) {
        if (end != null) {
            min *= 60;

            end = end == null ? LocalDateTime.now() : end;
            Duration duration = Duration.between(start, end);

            if (duration.toMinutes() < min) {
                end = end.minusSeconds(min);
            }
        }
    }

    @Override
    public String toString() {
        end = end == null ? LocalDateTime.now() : end;
        Duration duration = Duration.between(start, end);

        return start.format(FORMATTER) + ","
                + end.format(FORMATTER) + ","
                + durationFormat(duration);
    }

    private String durationFormat(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60,
                (s % 60));
    }

}
