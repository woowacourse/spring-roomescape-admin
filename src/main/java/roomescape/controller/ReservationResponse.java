package roomescape.controller;

import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse of(final Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.dateTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reservation.dateTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
}
