package roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dao.Reservation;

public record ReservationFindAllResponse(Long id, String name, LocalDate date, LocalTime time) {
    public static ReservationFindAllResponse from(Reservation reservation) {
        return new ReservationFindAllResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
