package roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dao.Reservation;

public record ReservationFindResponse(Long id, String name, LocalDate date, LocalTime time) {
    public static ReservationFindResponse from(Reservation reservation) {
        return new ReservationFindResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
