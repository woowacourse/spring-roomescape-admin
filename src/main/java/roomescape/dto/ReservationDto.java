package roomescape.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationDto(long id, String name, LocalDate date, String time) {

    public static ReservationDto of(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return new ReservationDto(
                reservation.id(), reservation.name(), reservation.date(),
                reservation.time().format(formatter)
        );
    }
}
