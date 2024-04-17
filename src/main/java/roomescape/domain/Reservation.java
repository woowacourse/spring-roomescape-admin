package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
