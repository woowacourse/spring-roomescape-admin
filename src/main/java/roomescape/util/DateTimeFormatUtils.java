package roomescape.util;

import java.time.format.DateTimeFormatter;

public final class DateTimeFormatUtils {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeFormatUtils() {
    }
}
