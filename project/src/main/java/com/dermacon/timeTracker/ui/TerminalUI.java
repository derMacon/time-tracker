package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.logic.task.Session;
import com.dermacon.timeTracker.logic.duration.DurationTask;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TerminalUI implements UserInterface {

    private static final String INTRO = "time-tracker v1.0\n";
    private static final String MANUAL = "usage:\n" +
            "  - p: pause (wip)\n" +
            "  - i: insert pause (wip)\n" +
            "  - q: quit without editing\n" +
            "  - e: quit with editing\n";



    private static final int FST_COL_WIDTH = 5;
    private static final int SND_COL_WIDTH = 20;
    private static final int THRD_COL_WIDTH = 20;
    private static final int FRTH_COL_WIDTH = 20;

    private static final int DISPLAY_INTERVAL = 1000;

    private Timer t = new Timer(true);


    @Override
    public void displayInfo(DurationTask bundle) {
        System.out.println(INTRO + drawGeneralInfo(bundle));
    }




    @Override
    public void displayOptions(Map<String, List<String>> table) {

        List<Integer> width_columns = new ArrayList<>();
        List<String> words = new LinkedList<>();
        Integer longestWordLen;

        for (Map.Entry<String, List<String>> entry : table.entrySet()) {
            words.add(entry.getKey());
            words.addAll(entry.getValue());

            longestWordLen = words.stream()
                    .max(Comparator.comparing(String::length))
                    .get().length() + 2;
            width_columns.add(longestWordLen);

            words.clear();
        }

        // Header
        String horiDivider = drawHoriDivider(width_columns);
        StringBuilder table_print = new StringBuilder(horiDivider);
        List<String> header = new ArrayList<>(table.keySet());
        table_print.append(drawTableLine(header, width_columns));
        table_print.append(horiDivider);

        // rest of table
        List<Map.Entry<String, List<String>>> body = new ArrayList<>(table.entrySet());
        int height = body.get(0).getValue().size();

        List<String> currLine = new ArrayList<>();
        String formatted_line;

        for (int i = 0; i < height; i++) {
            for (Map.Entry<String, List<String>> entry : body) {
                currLine.add(entry.getValue().get(i));
            }

            formatted_line = drawTableLine(currLine, width_columns);
            currLine.clear();

            table_print.append(formatted_line);
        }

        table_print.append(horiDivider);

        System.out.println(table_print);
    }

    private String drawHoriDivider(List<Integer> col_width) {
        StringBuilder out = new StringBuilder("+");

        for(Integer currWidth : col_width) {
            for (int i = 0; i < currWidth; i++) {
                out.append('-');
            }
            out.append("+");
        }

        return out.toString() + "\n";
    }

    private String drawTableLine(List<String> entries, List<Integer> col_width) {
        assert entries != null && col_width != null
                && entries.size() == col_width.size();

        String out = "|";
        int diff;
        String currWord;
        String fstPadding, sndPadding;

        for (int i = 0; i < entries.size(); i++) {
            currWord = entries.get(i);

            diff = col_width.get(i) - currWord.length();
            // appends n blanks
            fstPadding = IntStream.range(0, diff / 2)
                    .mapToObj(n -> " ").collect(Collectors.joining(""));
            sndPadding = IntStream.range(0, diff - (diff / 2))
                    .mapToObj(n -> " ").collect(Collectors.joining(""));

            out += fstPadding + currWord + sndPadding + "|";
        }

        return out + "\n";
    }


    private String drawGeneralInfo(DurationTask bundle) {
        String total = "total: " + formatDuration(bundle.getTotal()) + "\n";
        String today = "today: " + formatDuration(bundle.getToday()) + "\n\n";
        return drawFrame(total + today);
    }

    // todo maybe delete this
    public String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d:%02d",
                s / (3600 * 24),
                s / 3600,
                (s % 3600) / 60,
                (s % 60));
    }

    private String drawFrame(String in) {
        // todo
        return in;
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
    public InteractionMode waitForUserAbortion() {

        System.out.println("\n" + MANUAL);

        Scanner s = new Scanner(System.in);
        String userInteraction;
        do {
            userInteraction = s.next();
        } while (!userInteraction.equals("q") && !userInteraction.equals("e"));

//        task.stopTask();
//
//        // user wants to edit ending time
//        if (userInteraction.equals("e")) {
//            editEndingTime(task);
//        }

        InteractionMode out = InteractionMode.QUIT_DIRECT;
        if (userInteraction.equalsIgnoreCase("e")) {
            out = InteractionMode.QUIT_EDIT;
        }
        return out;
    }


    @Override
    public int editEndingTime(String task_name) {
        System.out.println("Edit current task: " + task_name + "\n"
                + "type the number of minutes you want to subtract from the " +
                "end time");
        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            userInput = s.nextLine();
        } while (!userInput.matches("\\d+"));

        return Integer.parseInt(userInput);
    }


    @Override
    public void startTimerDisplay(Session task) {
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (task.isRunning()) {
                    System.out.print("user input [" + task.displayPassedTime()
                            + "] > \r");
                }
            }
        }, 10, DISPLAY_INTERVAL);
    }


    @Override
    public void endTimerDisplay(Session task) {
        t.cancel();
    }
}
