package roomescape.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(String name, LocalDateTime dateTime) {
        this(null, name, dateTime);
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    public String formatDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
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
