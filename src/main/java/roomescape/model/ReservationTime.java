package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        id = null;
        this.startAt = startAt;
    }

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, Long id) {
        if (reservationTime.isEntity()) {
            throw new IllegalArgumentException("이미 Entity화 되어있는 객체입니다.");
        }
        return new ReservationTime(id, reservationTime.startAt);
    }

    private boolean isEntity() {
        return id != null;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
