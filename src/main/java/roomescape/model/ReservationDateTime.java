package roomescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {

    private final LocalDateTime reservationDateTime;

    public ReservationDateTime(final LocalDateTime reservationDateTime) {
        this.reservationDateTime = Objects.requireNonNull(reservationDateTime);
    }

    public LocalDate getDate() {
        return reservationDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.toLocalTime();
    }

}
