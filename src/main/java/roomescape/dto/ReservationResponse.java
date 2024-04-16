package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReserveTime;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        Long id = reservation.getId();
        Name name = reservation.getName();
        ReserveTime reserveTime = reservation.getReserveTime();
        return new ReservationResponse(id, name.asText(), reserveTime.getDate(), reserveTime.getTime());
    }
}
