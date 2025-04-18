package roomescape.reservation.controller.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        LocalDate date = reservation.getDateTime().toLocalDate();
        LocalTime time = reservation.getDateTime().toLocalTime();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return new ReservationResponse(
                reservation.getId(),
                reservation.getReserverName(),
                date.toString(),
                time.format(timeFormatter)
        );
    }
}
