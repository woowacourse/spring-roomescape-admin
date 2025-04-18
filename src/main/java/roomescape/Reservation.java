package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private static final AtomicLong index = new AtomicLong(1);
    private static final LocalTime runningTime = LocalTime.of(2, 0);
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

    public boolean isDuplicatedWith(Reservation other) {
        LocalTime endTime = time.plusSeconds(runningTime.toSecondOfDay());
        LocalTime otherStartTime = other.time;
        return otherStartTime.toNanoOfDay() >= time.toNanoOfDay()
                && otherStartTime.toNanoOfDay() < endTime.toNanoOfDay();
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
