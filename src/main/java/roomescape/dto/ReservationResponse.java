package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(Reservation reservation) {
        String time = reservation.getTime().format(FORMATTER);
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), time);
    }
}
