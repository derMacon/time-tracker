package com.dermacon.timeTracker.logic.task;

import com.dermacon.timeTracker.io.CSVReader;
import com.dermacon.timeTracker.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SessionFactory {

    private static final String START_TIMESTAMP = "start";
    private static final String FINISH_TIMESTAMP = "finish";


    public static List<Session> createSessionLst() throws IOException {
        List<Session> out = new LinkedList<>();
        List<File> files = FileHandler.getTrackedFiles();

        for (File file : files) {
            Map<String, List<String>> entries = CSVReader.readCSV(file);

            List<String> start_timeStamps = entries.get(START_TIMESTAMP);
            List<String> finish_timeStamps = entries.get(FINISH_TIMESTAMP);

            out.add(createSingleSession(file, start_timeStamps, finish_timeStamps));
        }

        return out;
    }

    private static Session createSingleSession(File file, List<String> start, List<String> finish) {
        assert start != null && finish != null && start.size() == finish.size();
        Session out = new Session(file);

        for (int i = 0; i < start.size(); i++) {
            out.addTrackingTask(createTrackedTask(start.get(i), finish.get(i)));
        }

        return out;
    }

    private static TrackingTask createTrackedTask(String start_timestamp, String finish_timestamp) {
        LocalDateTime start_time = createLocalDateTime(start_timestamp);
        LocalDateTime finish_time = createLocalDateTime(finish_timestamp);
        return new TrackingTask(start_time, finish_time);
    }



    /**
     * https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
     *
     * parses the given String to an instance of LocalDateTime.
     *
     * @param entry String content to parse
     * @return new LocalDateTime instance
     */
    private static LocalDateTime createLocalDateTime(String entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy" +
                " HH:mm:ss");
        return LocalDateTime.parse(entry, formatter);
    }

    /**
     * Creates the diff duration from two given LocalDate lists. The first list contains the
     * start timestamps and the second list contains the corresponding ending timestamps.
     * Those time differences will be added for those durations where the start timestamp
     * occurred on the same day as the current timestamp.
     * @param start start timestamps of the durations
     * @param finish end timestamps of the durations
     * @param predicate predicate that needs to be fulfilled for the start timestamp
     * @return summed up durations provided by the two lists
     */
    private static Duration sumUpDiff(List<LocalDateTime> start,
                                      List<LocalDateTime> finish,
                                      Predicate<LocalDateTime> predicate) {
        assert start.size() == finish.size();
        Duration out = Duration.ZERO;
        for (int i = 0; i < start.size(); i++) {
            if (predicate.test(start.get(i))) {
                out = out.plus(Duration.between(start.get(i), finish.get(i)));
            }
        }
        return out;
    }


}
