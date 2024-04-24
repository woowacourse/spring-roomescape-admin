package roomescape.time.dto;

import roomescape.time.domain.ReservationTime;

public record ReservationTimeResponseDto(long id, String startAt) {

    public static ReservationTimeResponseDto from(final ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.id(), reservationTime.startAt().toString());
    }
}
