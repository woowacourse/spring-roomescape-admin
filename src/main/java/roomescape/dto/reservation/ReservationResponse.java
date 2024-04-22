package roomescape.dto.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.reservation.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, String time) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getReservationDate(),
                reservation.getReservationTime().format(DATE_TIME_FORMATTER)
        );
    }
}
