package roomescape.reservation.dto;

import roomescape.reservation.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, LocalTime startAt){

    public static ReservationTimeResponseDto toDto(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt());
    }
}
