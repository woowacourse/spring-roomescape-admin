package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    public Reservation {
        validateNotBlank(name, "이름");
        validateNotBlank(date, "날짜");
        validateReservationTimeNotNull(time);
    }

    private static void validateNotBlank(String target, String fieldName) {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 도메인에서 필수값이며 공백일 수 없습니다.");
        }
    }

    private static void validateReservationTimeNotNull(ReservationTime reservationTime) {
        if (reservationTime == null) {
            throw new IllegalArgumentException("time은 도메인에서 필수값이며 null일 수 없습니다.");
        }
    }

    public static Reservation constructWithNoId(String name, String date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }
}
