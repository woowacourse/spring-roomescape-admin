package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservationRequest(
        String name,
        String date,
        String time
) {

    public Reservation toReservation(long id) {
        LocalDateTime localDateTime = LocalDateTime.parse(date + time, DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"));
        return new Reservation(id, name, localDateTime);
    }
}
