package roomescape.reservation.utils;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

public class ReservationMapper {

    public static Reservation toReservation(ReservationRequest reservationRequest, long index) {
        return new Reservation(index, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
    }

    public static ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
