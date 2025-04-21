package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(final Long id, final Reservation reservation) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
