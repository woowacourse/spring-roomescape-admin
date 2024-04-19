package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(String name, LocalDate date, LocalTime time) {

    public Reservation createReservation(long index) {
        return new Reservation(index, name, date, time);
    }
}
