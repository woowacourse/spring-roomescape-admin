package roomescape;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    private ReservationTime() {
    }

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, Long id) {
        return new ReservationTime(id, reservationTime.startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt.toString();
    }
}
