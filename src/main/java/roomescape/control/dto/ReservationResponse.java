package roomescape.control.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse of(Reservation reservation) {
        final LocalDateTime reservationDateTime = reservation.getReservationDateTime();
        final String date = reservationDateTime.toLocalDate().toString();
        final String time = reservationDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return new ReservationResponse(reservation.getId(), reservation.getReservationName(), date, time);
    }
}
