package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TextFixture {

    private static final String DATE_FORMAT = "%d-%02d-%02d";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String makeNowTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String makeTodayMessage() {
        LocalDate today = LocalDate.now();
        return String.format(DATE_FORMAT, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    public static String makeYesterdayMessage() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return String.format(DATE_FORMAT, yesterday.getYear(), yesterday.getMonthValue(), yesterday.getDayOfMonth());
    }
}
