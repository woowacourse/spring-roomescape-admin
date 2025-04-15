package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTime {

    private final LocalDateTime reservationTime;

    public ReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalDate getDate() {
        return reservationTime.toLocalDate();
    }

    public LocalTime getTime() {
        return reservationTime.toLocalTime();
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
}
