package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getReservationId(),
                reservation.getCustomerName(),
                reservation.getReservationTime().startTime().toLocalDate(),
                reservation.getReservationTime().startTime().toLocalTime());
    }
}
