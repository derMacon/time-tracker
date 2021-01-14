package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.ErrorCode;
import com.dermacon.timeTracker.logic.TrackingLogic;
import com.dermacon.timeTracker.logic.commands.TimerToken;
import com.dermacon.timeTracker.logic.task.Activity;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

public class TrackingController {

    private final UserInterface ui;
    private final TrackingLogic logic;

    public TrackingController(UserInterface ui, TrackingLogic logic) {
        this.ui = ui;
        this.logic = logic;
    }

    public void run() {
        Activity currActivity = logic.getSelectedActivity();
        currActivity.startNewTask();

        ui.displayCurrActivity(currActivity);
        ui.displayManual();

        Scanner sc = new Scanner(System.in);
        do {
            ui.startTimerDisplay(currActivity);
            parse(sc);
        } while (logic.isRunning());
    }

    private void parse(Scanner sc) {
        String input = sc.nextLine();

        Optional<TimerToken> tt = Arrays.stream(TimerToken.values())
                .filter(e -> e.matches(input))
                .findFirst();

        if (!tt.isPresent()) {
            ui.displayErrorMessage(ErrorCode.INVALID_TIMER_INPUT);
        }

        switch (tt.get()) {
            case TOGGLE_PAUSE:
                logic.toggle();
                break;
            case INSERT_PAUSE:
                // todo
                break;
            case QUIT_TASK:
                // todo
                break;
            case QUIT_WITH_EDIT:
                // todo
                break;
            default:
                // todo
        }


    }


}
