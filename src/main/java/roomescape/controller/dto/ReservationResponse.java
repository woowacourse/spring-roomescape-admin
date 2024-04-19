package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReserveTime;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        Name name = reservation.getName();
        ReserveTime reserveTime = reservation.getReserveTime();
        return new ReservationResponse(
                reservation.getId(), name.asText(), reserveTime.getDate(), reserveTime.getTime()
        );
    }
}
