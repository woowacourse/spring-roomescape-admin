package roomescape.domain;

import java.time.LocalDate;

public class ReservationTime {
    private Long id;
    private LocalDate startAt;

    public ReservationTime() {
    }

    public ReservationTime(Long id, LocalDate startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

}
