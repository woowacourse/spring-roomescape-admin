package roomescape.reservation.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validateReservationTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public static void validateReservationTime(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("startAt cannot be null");
        }
    }

    public static ReservationTime withoutId(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public boolean existId() {
        return id != null;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
