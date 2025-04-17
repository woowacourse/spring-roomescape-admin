package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, LocalDate localDate, LocalTime localTime) {

    public static ReservationResponse toDto(Long id, Reservation reservation) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
