package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record SaveReservationRequest(String name, String date, Long timeId, String startAt) {

    public static Reservation toEntity(SaveReservationRequest request, ReservationTime reservationTime) {
        return new Reservation(request.name(), request.date(), reservationTime);
    }
}
