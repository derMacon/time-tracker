package com.dermacon.timeTracker.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MenuToken {
    SELECT("select (\\d+)", "select activity"),
    DELETE("delete (\\d+)", "delete activity"),
    CREATE("create ([a-z]+)", "create new activity");

    private final String regex;
    private final String description;

    MenuToken(String regex, String description) {
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

