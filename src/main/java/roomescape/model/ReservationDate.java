package roomescape.model;

import java.time.LocalDate;

public class ReservationDate {
    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

}
