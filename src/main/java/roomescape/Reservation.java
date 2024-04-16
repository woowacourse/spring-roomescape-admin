package roomescape;

import java.time.LocalDateTime;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
