package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, LocalTime time) {

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
