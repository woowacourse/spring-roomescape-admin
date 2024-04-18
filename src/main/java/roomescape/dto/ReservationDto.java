package roomescape.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.reservation.Reservation;

public record ReservationDto(Long id, String name, LocalDate date, String time) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime().format(DATE_TIME_FORMATTER)
        );
    }
}
