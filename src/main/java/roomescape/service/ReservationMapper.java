package roomescape.service;

import roomescape.controller.reservation.ReservationRequest;
import roomescape.controller.reservation.ReservationResponse;
import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;

public class ReservationMapper {

    public static Reservation map(ReservationRequest request) {
        return new Reservation(null, request.name(), request.date(), request.time());
    }

    public static ReservationResponse map(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reservation.time().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
