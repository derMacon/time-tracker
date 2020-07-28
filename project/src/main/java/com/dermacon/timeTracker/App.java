package com.dermacon.timeTracker;

import com.dermacon.timeTracker.ui.InputController;
import com.dermacon.timeTracker.ui.TerminalUI;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        new InputController(new TerminalUI()).start();
    }

}
