package com.dermacon.timeTracker.exception;

public class InvalidCSVFormattingException extends TimeTrackerException {
    public InvalidCSVFormattingException() {
        super(ErrorCode.INVALID_CSV_FORMATTING);
    }
}
