package com.dermacon.timeTracker.durations;

import com.dermacon.timeTracker.CSVReader;
import com.dermacon.timeTracker.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// todo check if pattern implementation correct
public class DurationFactory {

    private static final int NEW_DAY_HOUR = 8;

    public static DurationTask createDurationTask() throws IOException {
        List<File> files = FileHandler.getTrackedFiles();

        DurationTask output = new DurationBundle();
        for (File file : files) {
            Map<String, List<String>> entries = CSVReader.readCSV(file);

            List<String> start_string = entries.get("start");
            List<LocalDateTime> start_timeStamps = start_string.stream()
                    .map(DurationFactory::createLocalDateTime)
                    .collect(Collectors.toList());

            List<String> finish_string = entries.get("finish");
            List<LocalDateTime> finish_timeStamps = finish_string.stream()
                    .map(DurationFactory::createLocalDateTime)
                    .collect(Collectors.toList());

            Duration duration_total = sumUpDiff(start_timeStamps,
                    finish_timeStamps, (e -> true));
            Duration duration_today = sumUpDiff(start_timeStamps,
                    finish_timeStamps, (e -> e.getHour() > NEW_DAY_HOUR));

            output.add(new DurationSingle(duration_total, duration_today, file));
        }

        return output;
    }

    /**
     * https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
     *
     * @param entry
     * @return
     */
    private static LocalDateTime createLocalDateTime(String entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy" +
                " HH:mm:ss");
        return LocalDateTime.parse(entry, formatter);
    }


    private static Duration sumUpDiff(List<LocalDateTime> start,
                                      List<LocalDateTime> finish,
                                      Predicate<LocalDateTime> predicate) {
        assert start.size() == finish.size();

        Duration out = Duration.ZERO;
        for (int i = 0; i < start.size(); i++) {
            // todo maybe check if both are in the same day
            if (predicate.test(start.get(i))) {
                out = out.plus(Duration.between(start.get(i), finish.get(i)));
            }
        }
        return out;
    }

}
