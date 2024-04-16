package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDateTime timestamp;

    public Reservation(long id, String name, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
