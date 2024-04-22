package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;
import roomescape.ReservationTime;

public record ReservationRequest(
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        long timeId) {
    public Reservation toReservation(LocalTime time) {
        return new Reservation(name, date, new ReservationTime(time));
    }

    /*public Reservation toReservation() {
        return new Reservation(name, date, new ReservationTime(time));
    }*/
}
