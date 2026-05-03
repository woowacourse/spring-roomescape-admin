package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeDetailDto(
        Long id,
        LocalTime startAt
) {

    public static ReservationTimeDetailDto from(ReservationTime reservationTime) {
        return new ReservationTimeDetailDto(reservationTime.getId(), reservationTime.getStartAt());
    }

}
