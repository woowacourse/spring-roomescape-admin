package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }
}
