package roomescape;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private final AtomicLong id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final AtomicLong id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public AtomicLong getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
