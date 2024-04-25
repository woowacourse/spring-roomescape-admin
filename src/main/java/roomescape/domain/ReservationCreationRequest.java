package roomescape.domain;

import java.time.LocalDate;

public class ReservationCreationRequest {
    private final Name name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationCreationRequest(String name, LocalDate date, Long timeId) {
        this.name = new Name(name);
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
