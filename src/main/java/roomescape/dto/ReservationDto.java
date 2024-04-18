package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(Long id, String name, LocalDate date, LocalTime time) {
    public static ReservationDto from(final Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getClientName().getValue(),
                reservation.getTime().toLocalDate(),
                reservation.getTime().toLocalTime()
        );
    }
}
