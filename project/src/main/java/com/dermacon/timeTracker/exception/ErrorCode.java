package com.dermacon.timeTracker.exception;

public enum ErrorCode {
    CSV_NOT_FOUND("csv not found", "csv at the given path does not exist"),
    INVALID_CSV_FORMATTING("Invalid csv", "A csv header must at least haven a table header"),
    INVALID_MENU_INPUT("invalid user input", "no matcher found for given user input, try again."),
    INVALID_TIMER_INPUT("invalid timer input", "invalid user input, try again.");

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
