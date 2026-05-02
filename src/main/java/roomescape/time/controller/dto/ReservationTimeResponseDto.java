package roomescape.time.controller.dto;

import roomescape.time.domain.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeResponseDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
