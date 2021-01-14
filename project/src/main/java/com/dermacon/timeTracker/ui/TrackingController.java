package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.ErrorCode;
import com.dermacon.timeTracker.logic.TrackingLogic;
import com.dermacon.timeTracker.logic.commands.TimerToken;
import com.dermacon.timeTracker.logic.task.Activity;

import java.util.Arrays;
import java.util.Optional;

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

        do {
            ui.startTimerDisplay(currActivity);
            parseTimerInput();
        } while (logic.isRunning());
    }

    private void parseTimerInput() {
        String input = ui.timerInput();

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
