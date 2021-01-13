package com.dermacon.timeTracker.logic.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.dermacon.timeTracker.ui.StringUtils.DURATION_FORMAT;

/**
 * Task that will serve as a time measure unit in the system.
 * When the tracking process starts, initially only the time
 * stamp will be set. Only when the task is being stopped the
 * finishing timestamp will be set.
 */
public class TrackingTask {

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DURATION_FORMAT);

    private LocalDateTime start;
    private LocalDateTime end;

    public TrackingTask() {
        this(LocalDateTime.now(), null);
    }

    public TrackingTask(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isRunning() {
        return end == null;
    }

    public void stopTask() {
        this.end = LocalDateTime.now();
    }

    public void addMinutes(int min) {
        if (end != null) {
            min *= 60;

            end = end == null ? LocalDateTime.now() : end;
            Duration duration = Duration.between(start, end);

            if (duration.toMinutes() < min) {
                end = end.plusSeconds(min);
            }
        }
    }

    public Duration getDuration() {
        LocalDateTime temp = end == null ? LocalDateTime.now() : end;
        return Duration.between(start, temp);
    }

    public static String createFormattedStr(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%d:%02d:%02d",
                s / 3600, (s % 3600) / 60, (s % 60));
    }

    @Override
    public String toString() {
        LocalDateTime temp = end == null ? LocalDateTime.now() : end;
        String duration_str = createFormattedStr(getDuration());

        return start.format(FORMATTER) + ","
                + temp.format(FORMATTER) + ","
                + duration_str;
    }

}
