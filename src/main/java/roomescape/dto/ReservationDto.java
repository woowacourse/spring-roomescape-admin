package roomescape.dto;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
    public static ReservationDto from(Reservation entity) {
        return new ReservationDto(entity.getDate(), entity.getName(), entity.getTime());
    }

    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
