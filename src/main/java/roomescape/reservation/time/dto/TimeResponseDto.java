package roomescape.reservation.time.dto;

import roomescape.reservation.time.ReservationTime;

public record TimeResponseDto(
        Long id,
        String startAt
) {
    public static TimeResponseDto from(ReservationTime reservationTime) {
        return new TimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
