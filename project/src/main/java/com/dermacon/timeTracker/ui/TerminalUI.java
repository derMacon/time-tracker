package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.exception.ErrorCode;
import com.dermacon.timeTracker.logic.commands.MenuToken;
import com.dermacon.timeTracker.logic.commands.TimerToken;
import com.dermacon.timeTracker.logic.task.Activity;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static com.dermacon.timeTracker.ui.StringUtils.convertToPrintableStr;

/**
 * User interface implementation for the terminal window
 */
public class TerminalUI implements UserInterface {

    private static final String IDX_KEY = "idx";
    private static final String NAME_KEY = "name";
    private static final String TODAY_KEY = "today";
    private static final String TOTAL_KEY = "total";

    private static final String INTRO = "time-tracker v1.0\n";
    private final static String INPUT_CURSOR = "input: ";
    private final static String MENU_FORMAT = "  - %s: %s\n";

    private static final int DISPLAY_INTERVAL = 1000;

    private Timer timer = new Timer(true);
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayManual() {
        String out = INTRO;
        for (TimerToken tt : TimerToken.values()) {
            out += String.format(MENU_FORMAT, tt.getRegex(), tt.getDescription());
        }
        System.out.println(out);
    }

    @Override
    public void displayMenuOptions() {
        String out = "options:\n";
        for (MenuToken menuToken : MenuToken.values()) {
            out += String.format(MENU_FORMAT,
                    menuToken.getRegex(),
                    menuToken.getDescription());
        }
        System.out.println(out);
    }

    @Override
    public void displayMenuCursor() {
        System.out.print(INPUT_CURSOR);
    }

    @Override
    public void displayTotalDuration(Duration total) {
        System.out.println(formatDuration(total));
    }

    @Override
    public void displayTodayDuration(Duration today) {
        System.out.println(formatDuration(today));
    }

    @Override
    public void displayActivities(List<Activity> trackedActivities) {
        Map<String, List<String>> table = convertSessionsToMap(trackedActivities);
        String printableStr = convertToPrintableStr(table);
        System.out.println(printableStr);
    }

    @Override
    public void displayCurrActivity(Activity activity) {
        System.out.println("current activity: " + activity);
    }

    /**
     * Converts a list of sessions to a map which can be displayed in a table
     *
     * @param activities List of sessions that will be processed
     * @return a list of sessions to a map which can be displayed in a table
     */
    private static Map<String, List<String>> convertSessionsToMap(List<Activity> activities) {
        // important, keep keys in order they were inserted (LinkedHashMap)
        Map<String, List<String>> table = new LinkedHashMap<>();

        List<String> idx = new LinkedList<>();
        List<String> name = new LinkedList<>();
        List<String> today = new LinkedList<>();
        List<String> total = new LinkedList<>();
        int i = 1;

        for (Activity currActivity : activities) {
            idx.add(String.valueOf(i++));
            name.add(currActivity.getFile().getName());
            today.add(formatDuration(currActivity.getTodayDuration()));
            total.add(formatDuration(currActivity.getTotalDuration()));
        }

        table.put(IDX_KEY, idx);
        table.put(NAME_KEY, name);
        table.put(TODAY_KEY, today);
        table.put(TOTAL_KEY, total);
        return table;
    }

    /**
     * Formats a given duration to a String that can be displayed
     *
     * @param duration duration that will be formatted
     * @return a given duration to a String that can be displayed
     */
    private static String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d:%02d",
                s / (3600 * 24),
                (s / 3600) % 24,
                (s % 3600) / 60,
                (s % 60));
    }

    @Override
    public int selectTask() {
        System.out.print("user input: ");

        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            userInput = s.nextLine();
            // todo check if valid idx
        } while (!userInput.matches("\\d+"));

        return Integer.parseInt(userInput);
    }

    @Override
    public void waitForResume() {
        System.out.print("Task paused, press any key to resume: ");
        scanner.nextLine();
    }

    @Override
    public int editEndingTime(String task_name) {
        System.out.println("Edit current task: " + task_name + "\n"
                + "type the number of minutes you want to subtract from the " +
                "end time");
        String userInput;
        do {
            userInput = scanner.nextLine();
        } while (!userInput.matches("\\d+"));

        return Integer.parseInt(userInput);
    }

    private Activity act;

    @Override
    public void startTimerDisplay(Activity task) {
        act = task;
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.print("user input [" + formatDuration(task.getTodayDuration())
//                        + "] > \r");
//            }
//        }, 10, DISPLAY_INTERVAL);
    }

    @Override
    public void endTimerDisplay(Activity task) {
        timer.cancel();
    }

    @Override
    public void displayErrorMessage(ErrorCode error) {
        System.out.println("Error: " + error.toString());
    }




    // ------------------- input ------------------- //

    @Override
    public String menuInput() {
        return scanner.nextLine();
    }


    @Override
    public String timerInput() {
        KeyLogger k = new KeyLogger(act);
        k.startTimerDisplay(act);

//        timer.cancel();
        return scanner.nextLine();
    }





}
