package com.dermacon.timeTracker;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.TrackingTask;
import com.dermacon.timeTracker.ui.InputController;
import com.dermacon.timeTracker.ui.TerminalUI;
import com.dermacon.timeTracker.ui.UserInterface;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        new InputController(new TerminalUI()).start();
    }

}
