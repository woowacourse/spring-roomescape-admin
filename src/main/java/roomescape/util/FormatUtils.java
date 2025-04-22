package roomescape.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtils {

    private FormatUtils() {}

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);
}
