package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    public Reservation toReservation() {
        return new Reservation(id, name, date, time);
    }
}
