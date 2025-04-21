package roomescape.reservation.controller.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationCreateRequest(
        String name,
        String date,
        String time
) {
    public Reservation to() {
        return new Reservation(
                name, LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time))
        );
    }
}
