package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeResDto {

    private Long id;
    private LocalTime startAt;

    private ReservationTimeResDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResDto from(ReservationTime reservationTime) {
        return new ReservationTimeResDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
