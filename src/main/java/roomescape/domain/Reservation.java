package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate localDate;
    private final LocalTime localTime;

    public Reservation(Long id, String name, LocalDate localDate, LocalTime localTime) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
