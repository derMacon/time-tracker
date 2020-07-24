package com.dermacon.timeTracker.logic;

import com.dermacon.timeTracker.io.FileHandler;
import com.dermacon.timeTracker.logic.duration.DurationBundle;
import com.dermacon.timeTracker.logic.duration.DurationFactory;
import com.dermacon.timeTracker.logic.duration.DurationTask;
import com.dermacon.timeTracker.ui.InteractionMode;
import com.dermacon.timeTracker.ui.UserInterface;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrackingLogic {

    private static final String IDX_KEY = "idx";
    private static final String NAME_KEY = "name";
    private static final String TODAY_KEY = "today";
    private static final String TOTAL_KEY = "total";

    private final UserInterface ui;

    private DurationTask bundle;
    private TrackingTask task;


    public TrackingLogic(UserInterface ui) {
        this.ui = ui;

        try {
            // todo handle exception properly
            bundle = DurationFactory.createDurationTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenu() {

        // important, keep keys in order they were inserted (LinkedHashMap)
        Map<String, List<String>> table = new LinkedHashMap<>();

//        List<String> idx = IntStream.range(1, bundle.getChildrenCount())
//                .mapToObj(String::valueOf)
//                .collect(Collectors.toList());
//        table.put("idx", idx);

        List<String> idx = new LinkedList<>();
        List<String> name = new LinkedList<>();
        List<String> today = new LinkedList<>();
        List<String> total = new LinkedList<>();
        int i = 1;

        for (DurationTask currTask : bundle) {
            idx.add(String.valueOf(i++));
            name.add(currTask.getFile().getName());
            today.add(formatDuration(currTask.getToday()));
            today.add(formatDuration(currTask.getTotal()));
        }

        table.put(IDX_KEY, idx);
        table.put(NAME_KEY, name);
        table.put(TODAY_KEY, today);
        table.put(TOTAL_KEY, total);

        ui.displayInfo(bundle);
        ui.displayOptions(table);
    }

    private static String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d:%02d",
                s / (3600 * 24),
                s / 3600,
                (s % 3600) / 60,
                (s % 60));
    }


    public void selectTask(int userSelection) {
        File selectedFile = bundle.get(userSelection - 1).getFile();
        task = new TrackingTask(selectedFile);

        ui.startTimerDisplay(task);
    }

    public void handleQuitting(InteractionMode mode) {
        if (mode == InteractionMode.QUIT_EDIT) {
            ui.editEndingTime(task);
        }

        ui.endTimerDisplay(task);
        FileHandler.save(task);
    }



}
