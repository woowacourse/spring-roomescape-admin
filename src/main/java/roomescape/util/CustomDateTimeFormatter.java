package roomescape.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.reservation.domain.ReservationDate;
import roomescape.reservation.domain.ReservationTime;

public class CustomDateTimeFormatter {

    private static final String TIME_FORMAT_WITHOUT_SECOND = "HH:mm";

    private CustomDateTimeFormatter() {
    }

    // TODO: 도메인이 아니라 LocalDate로 변경하기
    public static String getFormattedDate(final ReservationDate date) {
        return date.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getFormattedTime(final ReservationTime time) {
        return time.getValue().format(DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_SECOND));
    }

    // TODO: 합치면 지우기
    public static String getFormattedTime(final LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_SECOND));
    }
}
