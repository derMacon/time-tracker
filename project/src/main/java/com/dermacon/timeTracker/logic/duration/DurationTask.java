package com.dermacon.timeTracker.logic.duration;

import java.io.File;
import java.time.Duration;

public interface DurationTask {

    Duration getTotal();
    Duration getToday();

    default File getFile() {
        throw new UnsupportedOperationException("add not supported on node");
    }

    default DurationTask add(DurationTask task) {
        throw new UnsupportedOperationException("add not supported on leaf");
    }

    default DurationTask get(int i) {
        throw new UnsupportedOperationException("add not supported on leaf");
    }

    default int getChildrenCount() {
        return 0;
    }

}
