package com.dermacon.timeTracker.ui;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import com.dermacon.timeTracker.logic.task.Activity;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Basic JNativeHook Template
 * https://github.com/vakho10/java-keylogger/blob/master/src/main/java/ge/vakho/KeyLogger.java
 * @author vakho
 *
 * Timer functionality added by myself
 */
public class TimerDisplay implements NativeKeyListener {

    private static final int DISPLAY_INTERVAL = 1000;

    private static Timer timer;
    public TimerDisplay() {}

    public TimerDisplay(Activity activity) {
        this.timer = new Timer();

        java.util.logging.Logger.getLogger("org.jnativehook").setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new TimerDisplay());
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        timer.cancel();
    }


    public void run(Activity task) {
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

