package roomescape.domain;

import java.util.regex.Pattern;

public record ReservationTime(
        Long id,
        String startAt
) {
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    public ReservationTime {
        validateStartAtNotBlank(startAt);
        validateTimeFormat(startAt);
    }

    private static void validateStartAtNotBlank(String startAt) {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("startAt은 도메인에서 필수값이며 공백일 수 없습니다.");
        }
    }

    private static void validateTimeFormat(String startAt) {
        if (!TIME_PATTERN.matcher(startAt).matches()) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다 : " + startAt);
        }
    }

    public static ReservationTime constructWithoutId(String startAt) {
        return new ReservationTime(null, startAt);
    }
}
