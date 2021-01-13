package com.dermacon.timeTracker.exception;

import static com.dermacon.timeTracker.exception.ErrorCode.CSV_NOT_FOUND;

public class CsvNotFoundException extends TimeTrackerException {
    public CsvNotFoundException() {
        super(CSV_NOT_FOUND);
    }
}
