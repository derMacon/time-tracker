package com.dermacon.timeTracker.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.logging.Level;

import com.dermacon.timeTracker.logic.task.Activity;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author vakho
 */
public class KeyLogger implements NativeKeyListener {

    private static final int DISPLAY_INTERVAL = 1000;

    private static Timer timer;
    public KeyLogger() {}

    public KeyLogger(Activity activity) {
        this.timer = new Timer();

        java.util.logging.Logger.getLogger("org.jnativehook").setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger());
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        timer.cancel();
    }


    public void startTimerDisplay(Activity task) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.print("user input [" + formatDuration(task.getTodayDuration())
                        + "] > \r");
            }
        }, 10, DISPLAY_INTERVAL);
    }

    private static String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d:%02d",
                s / (3600 * 24),
                (s / 3600) % 24,
                (s % 3600) / 60,
                (s % 60));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Nothing
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Nothing here
    }
}

