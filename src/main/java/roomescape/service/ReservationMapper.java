package roomescape.service;

import roomescape.controller.ReservationRequest;
import roomescape.controller.ReservationResponse;
import roomescape.entity.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationMapper {

    public static Reservation map(ReservationRequest request) {
        final LocalDateTime dateTime = request.date().atTime(request.time());
        return new Reservation(null, request.name(), dateTime);
    }

    public static ReservationResponse map(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.dateTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reservation.dateTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
}
