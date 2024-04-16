package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationDto(long id, String name, LocalDate date, LocalTime time) {
    public static ReservationDto from(Reservation reservation) {
        LocalDateTime timestamp = reservation.getTimestamp();

        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                timestamp.toLocalDate(),
                timestamp.toLocalTime()
        );
    }
}
