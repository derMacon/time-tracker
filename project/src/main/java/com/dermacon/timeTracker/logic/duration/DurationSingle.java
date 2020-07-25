package com.dermacon.timeTracker.logic.duration;

import java.io.File;
import java.time.Duration;
import java.util.Iterator;

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

    @Override
    public Iterator<DurationTask> iterator() {
        return new Iterator<DurationTask>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public DurationTask next() {
                return new DurationSingle(total, today, file);
            }
        };
    }
}
