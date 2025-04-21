package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDateTime {

    private final LocalDate date;
    private final ReservationTime reservationTime;

    public ReservationDateTime(LocalDate date, ReservationTime reservationTime) {
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return reservationTime.getStartAt();
    }
}
