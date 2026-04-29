package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeDetailDto(
        Long id,
        LocalTime time
) {

    public static ReservationTimeDetailDto from(ReservationTime reservationTime) {
        return new ReservationTimeDetailDto(reservationTime.getId(), reservationTime.getStartAt());
    }

}
