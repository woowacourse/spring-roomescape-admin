package roomescape.domain;

public record ReservationTime(
        Long id,
        String startAt) {

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

}
