package roomescape.domain;

import java.util.regex.Pattern;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public Reservation {
        validateNotBlank(name, "이름");
        validateNotBlank(date, "날짜");
        validateReservationTimeNotNull(time);
        validateDateFormat(date);
    }

    private static void validateNotBlank(String target, String fieldName) {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
        }
    }

    private static void validateReservationTimeNotNull(ReservationTime reservationTime) {
        if (reservationTime == null) {
            throw new NullPointerException("time은 도메인에서 필수값이며 null일 수 없습니다.");
        }
    }

    private static void validateDateFormat(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다: " + date);
        }
    }

    public static Reservation constructWithNoId(String name, String date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }
}
