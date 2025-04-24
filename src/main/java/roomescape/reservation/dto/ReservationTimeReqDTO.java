package roomescape.reservation.dto;

import roomescape.reservation.model.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeReqDTO(LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
