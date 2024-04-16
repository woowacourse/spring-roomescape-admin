package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime time;

    public Reservation(final Long id, final String name) {
        this.id = id;
        this.name = name;
        this.time = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return time.toLocalDate();
    }

    public LocalTime getTime() {
        return time.toLocalTime();
    }
}
