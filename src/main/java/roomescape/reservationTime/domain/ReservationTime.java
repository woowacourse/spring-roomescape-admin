package roomescape.reservationTime.domain;

import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private ReservationTime(LocalTime time) {
        this.id = null;
        this.startAt = time;
    }

    public static ReservationTime from(ReservationTimeReqDto reqDto) {
        return new ReservationTime(reqDto.startAt());
    }

    public boolean isSameTime(ReservationTime reservationTime) {
        return this.startAt.equals(reservationTime.startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
