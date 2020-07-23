package com.dermacon.timeTracker.durations;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class DurationSingle implements DurationTask {

    private final Duration total;
    private final Duration today;
    private final File file;


    public DurationSingle(Duration total, Duration today, File file) {
        this.total = total;
        this.today = today;
        this.file = file;
    }

    @Override
    public Duration getTotal() {
        return total;
    }

    @Override
    public Duration getToday() {
        return today;
    }

    @Override
    public File getFile() {
        return this.file;
    }
}
