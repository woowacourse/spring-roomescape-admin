package roomescape.dto;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequestDto(LocalTime startAt) {
    public ReservationTime toEntity(){
        return new ReservationTime(startAt);
    }
}
