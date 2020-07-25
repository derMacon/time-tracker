package com.dermacon.timeTracker.ui;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.TrackingTask;
import com.dermacon.timeTracker.logic.duration.DurationFactory;
import com.dermacon.timeTracker.logic.duration.DurationTask;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private static final String LINE_FORMAT = "|%1$-" + FST_COL_WIDTH + "s"
            + "|%2$-" + SND_COL_WIDTH + "s"
            + "|%3$-" + THRD_COL_WIDTH + "s"
            + "|%4$-" + THRD_COL_WIDTH + "s|\n";
    private static final int DISPLAY_INTERVAL = 1000;

    private Timer t = new Timer(true);


    @Override
    public void displayInfo(DurationTask bundle) {
        System.out.println(INTRO + drawGeneralInfo(bundle));
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

        int offset;
        String currWord;
        String padding = "";
        for (int i = 0; i < entries.size(); i++) {
            currWord = entries.get(i);

            offset = (col_width.get(i) - currWord.length()) / 2;
            for (int j = 0; j < offset; j++) {
                padding += " ";
            }

            out += padding + currWord + padding + "|";
        }

        return out + "\n";
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

        int col_cnt = width_columns.size();
        int overall_width = width_columns.stream().reduce(0, Integer::sum)
                + col_cnt + 1; // divider

        // Header
        String horiDivider = drawHoriDivider(width_columns);
        StringBuilder table_print = new StringBuilder(horiDivider);
        List<String> header = new ArrayList<>(table.keySet());
        table_print.append(drawTableLine(header, width_columns));
        table_print.append(horiDivider);

        // rest of table

        System.out.println(table_print);




//        StringBuilder options = new StringBuilder();
//        String hori_line = getHoriLine();
//
//        options.append(hori_line);
//
//        options.append(String.format(LINE_FORMAT,
//                StringUtils.center("idx",FST_COL_WIDTH),
//                StringUtils.center("name",SND_COL_WIDTH),
//                StringUtils.center("today",THRD_COL_WIDTH),
//                StringUtils.center("total",FRTH_COL_WIDTH)));
//
//        options.append(hori_line);
//
//
//        int cnt = bundle.getChildrenCount();
//
//        for (int i = 0; i < cnt; i++) {
//            options.append(String.format(LINE_FORMAT,
//                    StringUtils.center((1 + i) + "",FST_COL_WIDTH),
//                    StringUtils.center(bundle.get(i).getFile().getName(),
//                            SND_COL_WIDTH),
//                    StringUtils.center(formatDuration(bundle.get(i).getToday()),
//                            THRD_COL_WIDTH),
//                    StringUtils.center(formatDuration(bundle.get(i).getTotal()),
//                            FRTH_COL_WIDTH)));
//        }
//
//        options.append(String.format(LINE_FORMAT,
//                StringUtils.center((cnt + 1) + "",FST_COL_WIDTH),
//                StringUtils.center("new task",SND_COL_WIDTH),
//                StringUtils.center("-",THRD_COL_WIDTH),
//                StringUtils.center("-",FRTH_COL_WIDTH)));
//
//        options.append(hori_line);
//
//        return options.toString();
//        System.out.println("wip:\n" + table.toString());
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




    private String getHoriLine() {
        StringBuilder out = new StringBuilder("+");
        int line_width = FST_COL_WIDTH + SND_COL_WIDTH + THRD_COL_WIDTH + FRTH_COL_WIDTH;
        for (int i = 1; i <= line_width; i++) {
            if (i == FST_COL_WIDTH
                    || i == FST_COL_WIDTH + SND_COL_WIDTH
                    || i == FST_COL_WIDTH + SND_COL_WIDTH + THRD_COL_WIDTH
                    || i == FST_COL_WIDTH + SND_COL_WIDTH + THRD_COL_WIDTH + FRTH_COL_WIDTH
            ) {
                out.append("-+");
            } else {
                out.append("-");
            }
        }
        out.append("\n");
        return out.toString();
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
    public void startTimerDisplay(TrackingTask task) {
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
    public void endTimerDisplay(TrackingTask task) {
        t.cancel();
    }
}
