package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationEntity {

    private static final AtomicLong INDEX = new AtomicLong(1);

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationEntity(String name, LocalDate date, LocalTime time) {
        this.id = INDEX.getAndIncrement();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
