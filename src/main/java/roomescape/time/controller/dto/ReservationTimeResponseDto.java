package roomescape.time.controller.dto;

import roomescape.time.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, LocalTime startAt) {

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }
}
