package roomescape.reservation.time.dto;

import roomescape.reservation.time.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
