package com.dermacon.timeTracker.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Collective methods that manipulate Strings
 */
public class StringUtils {

    public final static String DURATION_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public final static String EMPTY_LINE_MESSAGE = "No entries yet";

    /**
     * Displaying options in a table
     * @param table map to display in the terminal
     */
    public static String convertToPrintableStr(Map<String, List<String>> table) {
        List<Integer> width_columns = calculateColumnWidth(table);

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

        if (height == 0) {
            table_print.append(drawEmptyTableLine(EMPTY_LINE_MESSAGE, width_columns));
        }

        table_print.append(horiDivider);

        return table_print.toString();
    }

    /**
     * Calculates the width of each column from a table
     * @param table table for which the column widths should be calculated
     * @return list of each width for the table columns
     */
    private static List<Integer> calculateColumnWidth(Map<String, List<String>> table) {
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
        return width_columns;
    }

    private static String drawTableLine(List<String> entries, List<Integer> col_width) {
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

    private static String drawEmptyTableLine(String message, List<Integer> col_width) {
        int overallWidth = col_width.stream()
                .collect(Collectors.summingInt(Integer::intValue));
        overallWidth += col_width.size() + 1;

        StringBuilder out = new StringBuilder("| ");
        char[] chars = message.toCharArray();
        for (int i = 0; i < overallWidth - 4; i++) {
            if (chars.length > i) {
                out.append(chars[i]);
            } else {
                out.append(" ");
            }
        }
        out.append(" |\n");
        return out.toString();
    }

    private static String drawHoriDivider(List<Integer> col_width) {
        StringBuilder out = new StringBuilder("+");

        for(Integer currWidth : col_width) {
            for (int i = 0; i < currWidth; i++) {
                out.append('-');
            }
            out.append("+");
        }

        return out.toString() + "\n";
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



    private String createFrame(String in) {
        // todo
        return in;
    }

    /**
     * https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
     *
     * parses the given String to an instance of LocalDateTime.
     *
     * @param entry String content to parse
     * @return new LocalDateTime instance
     */
    public static LocalDateTime createLocalDateTime(String entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DURATION_FORMAT);
        return LocalDateTime.parse(entry, formatter);
    }

}
