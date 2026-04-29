package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {

    public static ReservationTimeResponse fromEntity(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }

    public static List<ReservationTimeResponse> fromEntities(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::fromEntity)
                .toList();
    }
}
