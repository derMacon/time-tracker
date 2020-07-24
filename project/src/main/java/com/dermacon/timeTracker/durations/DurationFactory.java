package com.dermacon.timeTracker.durations;

import com.dermacon.timeTracker.CSVReader;
import com.dermacon.timeTracker.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
                    finish_timeStamps,
                    DurationFactory::isSameDay);
//                    (e -> e.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()));


            output.add(new DurationSingle(duration_total, duration_today, file));
        }

        return output;
    }

    private static boolean isSameDay(LocalDateTime timeStamp) {
        timeStamp = timeStamp.minusHours(NEW_DAY_HOUR);
        LocalDateTime fake_midnight = LocalDateTime.now().minusHours(NEW_DAY_HOUR);

        Duration diff = Duration.between(timeStamp, fake_midnight);

        return fake_midnight.getDayOfYear() == timeStamp.getDayOfYear()
                && !diff.isNegative() && diff.getSeconds() <= 24 * 60 * 60;

//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime morning = LocalDateTime.of(now.getYear(),
//                now.getMonth(), now.getDayOfMonth(), NEW_DAY_HOUR, 0, 0);
//        if (now.getHour() < NEW_DAY_HOUR) {
//            morning = morning.minusDays(1);
//        }
//        Duration duration_morning = Duration.between(morning, timeStamp);
//        return duration_morning.getSeconds() <= 24 * 60 * 60;
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
