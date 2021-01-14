package com.dermacon.timeTracker.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TimerToken {
    TOGGLE_PAUSE("p", "pause timer"),
    INSERT_PAUSE("i", "insert bygone pause"),
    QUIT_TASK("q", "quit task"),
    QUIT_WITH_EDIT("e", "quit with editing end time");

    private final String regex;
    private final String description;

    TimerToken(String regex, String description) {
        this.regex = regex;
        this.description = description;
    }

    public String getRegex() {
        return regex;
    }

    public String getDescription() {
        return description;
    }

//    public Matcher getMatcher(String input) {
//        Pattern p = Pattern.compile(regex);
//        return p.matcher(input);
//    }

    public boolean matches(String input) {
        Pattern p = Pattern.compile(regex);
        return p.matcher(input).matches();
    }

}
