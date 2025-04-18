package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private static final AtomicLong index = new AtomicLong(1);
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = index.getAndIncrement();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameWith(Reservation other) {
        return date.isEqual(other.date) && time.toNanoOfDay() == other.time.toNanoOfDay();
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
