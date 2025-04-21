package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public Reservation toEntity(Long id) {
        return new Reservation(
                id,
                name,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
