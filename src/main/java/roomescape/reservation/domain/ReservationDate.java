package roomescape.reservation.domain;

import java.time.LocalDate;

public class ReservationDate {
    private final LocalDate value;

    public ReservationDate(final LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }
}
