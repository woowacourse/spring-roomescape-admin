package roomescape.dto.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(Reservation reservation) {
        String time = TIME_FORMATTER.format(reservation.time());
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), time);
    }
}
