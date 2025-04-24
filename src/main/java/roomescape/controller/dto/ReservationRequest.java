package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.service.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation convertToReservation() {
        return new Reservation(name, date, timeId);
    }
}
