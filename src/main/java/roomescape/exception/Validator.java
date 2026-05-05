package roomescape.exception;

public final class Validator {
    private static final int YEAR_LENGTH = 4;
    private static final int MONTH_LENGTH = 2;
    private static final int DAY_LENGTH = 2;
    private static final int HOUR_LENGTH = 2;
    private static final int MINUTE_LENGTH = 2;

    private Validator() {
    }

    public static void validateDateFormat(String date) {
        if (!date.matches(String.format("\\d{%d}-\\d{%d}-\\d{%d}", YEAR_LENGTH, MONTH_LENGTH, DAY_LENGTH))) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 아닙니다.");
        }
    }

    public static void validateTimeFormat(String time) {
        if (!time.matches(String.format("\\d{%d}:\\d{%d}", HOUR_LENGTH, MINUTE_LENGTH))) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 아닙니다.");
        }
    }
}
