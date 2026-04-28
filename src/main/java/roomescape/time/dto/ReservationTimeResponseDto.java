package roomescape.time.dto;

import roomescape.time.entity.ReservationTime;

public record ReservationTimeResponseDto(
        Long id,
        String startAt
) {
    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt());
    }
}
