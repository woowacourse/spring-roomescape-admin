package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(String name, LocalDate date, LocalTime time) {

    public static ReservationRequestDto toDto(Reservation reservation) {
        return new ReservationRequestDto(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
