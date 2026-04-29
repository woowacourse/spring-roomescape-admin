package roomescape.domain;

public record ReservationTime(
        Long id,
        String startAt
) {
    public static ReservationTime constructWithoutId(String startAt) {
        return new ReservationTime(null, startAt);
    }
}
