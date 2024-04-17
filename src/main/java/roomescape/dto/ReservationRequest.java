package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public Reservation toInstance() {
        return new Reservation(name, LocalDate.parse(date), LocalTime.parse(time));
    }
}
