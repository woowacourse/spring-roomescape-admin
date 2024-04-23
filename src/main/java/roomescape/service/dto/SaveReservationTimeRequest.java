package roomescape.service.dto;

import roomescape.domain.ReservationTime;

public record SaveReservationTimeRequest(String startAt) {

    public static ReservationTime toEntity(SaveReservationTimeRequest request) {
        return new ReservationTime(request.startAt());
    }
}
