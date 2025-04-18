package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationEntity {
    private static final AtomicLong index = new AtomicLong(1);
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationEntity(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationEntity(String name, LocalDate date, LocalTime time) {
        this.id = index.getAndIncrement();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public LocalTime time() {
        return time;
    }
}
