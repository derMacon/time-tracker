package com.dermacon.timeTracker.exception;

public abstract class TimeTrackerException extends Exception {
    public TimeTrackerException(ErrorCode errorCode) {
        super(errorCode.toString());
    }
}
