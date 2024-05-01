package roomescape.core.service.response;

import roomescape.core.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt());
    }
}
