package roomescape.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateAndTimeConverter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate parseToDate(String input) {
        try {
            return LocalDate.parse(input, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 올바르지 않습니다)");
        }
    }

    public static LocalTime parseToTime(String input) {
        try {
            return LocalTime.parse(input, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다)");
        }
    }

    public static LocalDateTime parseToLocalDateTime(String input){
        try {
            return LocalDateTime.parse(input, dateAndTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다)");
        }
    }

    public static String formatDate(LocalDate date) {
        return date.format(dateFormatter);
    }

    public static String formatTime(LocalTime time) {
        return time.format(timeFormatter);
    }

    public static String formatDateAndTime(LocalDateTime localDateTime){
        return localDateTime.format(dateAndTimeFormatter);
    }
}
