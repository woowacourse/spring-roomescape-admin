package roomescape.model;

import java.time.LocalDate;

public final class ReservationDate {

    private final Long id;
    private final LocalDate startDate;

    public ReservationDate(Long id, LocalDate startDate) {
        this.id = id;
        this.startDate = startDate;
    }

    public ReservationDate(LocalDate startDate) {
        this.id = null;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
