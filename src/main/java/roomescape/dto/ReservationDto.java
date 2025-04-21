package roomescape.dto;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(reservation.date(), reservation.name(), reservation.time());
    }

    public Reservation toEntity() {
        return Reservation.of(name, LocalDateTime.of(date, time));
    }
}
