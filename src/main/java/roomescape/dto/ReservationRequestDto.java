package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation convertToReservation() {
        return new Reservation(
                this.name,
                new ReservationDateTime(LocalDateTime.of(this.date, this.time))
        );
    }
}
