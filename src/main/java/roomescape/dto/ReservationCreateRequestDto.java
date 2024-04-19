package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationCreateRequestDto(String name, LocalDate date, LocalTime time) {

    public Reservation createReservation(long index) {
        return new Reservation(index, name, date, time);
    }
}
