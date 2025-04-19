package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public ReservationTime getReservationTime() {
        return new ReservationTime(LocalDateTime.of(date, time));
    }
}
