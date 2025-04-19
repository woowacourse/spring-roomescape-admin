package roomescape.dto;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(reservation.getDate(), reservation.getName(), reservation.getTime());
    }

    public Reservation toEntity() {
        return new Reservation(name, LocalDateTime.of(date, time));
    }
}
