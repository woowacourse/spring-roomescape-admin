package roomescape.domain.reservationtime;

import lombok.Getter;

@Getter
public class ReservationTime {

    private final Long id;
    private final String startAt;

    private ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private ReservationTime(String startAt) {
        this.id = null;
        this.startAt = startAt;
    }

    public static ReservationTime createWithoutId(String startAt) {
        return new ReservationTime(startAt);
    }

    public static ReservationTime createWithId(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public static ReservationTime of(Long id, String startAt) {
        return new ReservationTime(id, startAt);
    }
}
