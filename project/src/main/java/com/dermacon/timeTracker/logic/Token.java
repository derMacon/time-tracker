package com.dermacon.timeTracker.logic;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
    SELECT("select (\\d+)", "select activity"),
    DELETE("delete (\\d+)", "delete activity"),
    CREATE("create (\\d+)", "create new activity");

    private final String regex;
    private final String description;

    Token(String regex, String description) {
        this.regex = regex;
        this.description = description;
    }

    public String getRegex() {
        return regex;
    }

    public String getDescription() {
        return description;
    }

    public Matcher getMatcher(String input) {
        Pattern p = Pattern.compile(regex);
        return p.matcher(input);
    }

}

