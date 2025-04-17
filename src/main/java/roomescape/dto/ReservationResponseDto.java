package roomescape.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponseDto(long id, String name, LocalDate date, String time) {

    public static ReservationResponseDto of(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return new ReservationResponseDto(
                reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime().format(formatter)
        );
    }
}
