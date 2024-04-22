package roomescape.util;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

public class CustomDateTimeFormatter {

    private static final String TIME_FORMAT_WITHOUT_SECOND = "HH:mm";

    private CustomDateTimeFormatter() {
    }

    public static String getFormattedDate(final ReservationDate date) {
        return date.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getFormattedTime(final ReservationTime time) {
        return time.getValue().format(DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_SECOND));
    }
}
