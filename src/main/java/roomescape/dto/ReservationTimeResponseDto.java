package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponseDto(
    long id,
    LocalTime startAt
) {

    public static ReservationTimeResponseDto from(final ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
