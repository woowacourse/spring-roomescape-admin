package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationRequest(
        String name,
        String date,
        Long timeId
) {
    public Reservation toEntityWithReservationTime(ReservationTime reservationTime) {
        return new Reservation(
                null,
                name,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservationTime
        );
    }
}
