package roomescape.reservation.time.dto;

import roomescape.reservation.time.ReservationTime;

import java.time.LocalTime;
import java.util.List;

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

    public static List<ReservationTimeResponseDto> from(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }
}
