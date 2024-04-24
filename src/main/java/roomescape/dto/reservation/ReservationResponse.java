package roomescape.dto.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dto.reservationtime.ReservationTimeResponse;

public record ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {
    public ReservationResponse(long id, String name, LocalDate date, long timeId, LocalTime time) {
        this(id, name, date, new ReservationTimeResponse(timeId, time));
    }
}
