package com.dermacon.timeTracker.exception;

public enum ErrorCode {
    CSV_NOT_FOUND("csv not found", "csv at the given path does not exist"),
    INVALID_CSV_FORMATTING("Invalid csv", "A csv header must at least haven a table header");

    private final String title;
    private final String description;

    ErrorCode(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return title + " - " + description;
    }
}
