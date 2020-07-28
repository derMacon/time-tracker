package com.dermacon.timeTracker.logic.duration;

import com.dermacon.timeTracker.io.CSVReader;
import com.dermacon.timeTracker.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// todo check if pattern implementation correct
/**
 * Factory for the DurationTask class
 */
public class DurationFactory {

    /**
     * Hour offset for midnight. A new day for the tracking task does not start at
     * 0 o'clock but at 8 am. Makes it possible to track a task from e.g. 11 pm to 3 am.
     */
    private static final int NEW_DAY_HOUR = 8;

    /**
     * Main method for creating Duration Task with the provided data by the
     * FileHandler
     * @return new DurationTask instance with data
     * @throws IOException Exception thrown when io does not behave as expected
     */
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
                    finish_timeStamps,
                    DurationFactory::isSameDay);

            output.add(new DurationSingle(duration_total, duration_today, file));
        }

        return output;
    }

    /**
     * Checks if a given timestamp occured on the same day as the current date.
     * Light modification: A new day does not start at 0 am but at NEW_DAY_HOUR am. This makes
     * it possible to track tasks in the middle of the night without any interruptions.
     * @param timeStamp timestamp to check
     * @return true if the timestamp occurred on the current day
     */
    private static boolean isSameDay(LocalDateTime timeStamp) {
        timeStamp = timeStamp.minusHours(NEW_DAY_HOUR);
        LocalDateTime fake_midnight = LocalDateTime.now().minusHours(NEW_DAY_HOUR);

        Duration diff = Duration.between(timeStamp, fake_midnight);

        return fake_midnight.getDayOfYear() == timeStamp.getDayOfYear()
                && !diff.isNegative() && diff.getSeconds() <= 24 * 60 * 60;
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
