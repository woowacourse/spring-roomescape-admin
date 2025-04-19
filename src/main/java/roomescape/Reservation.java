package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private static final AtomicLong index = new AtomicLong(1);
    private static final LocalTime runningTime = LocalTime.of(2, 0);
    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(String name, LocalDateTime dateTime) {
        this.id = index.getAndIncrement();
        this.name = name;
        this.dateTime = dateTime;
    }

    public boolean isDuplicatedWith(Reservation other) {
        LocalDateTime endTime = dateTime.plusSeconds(runningTime.toSecondOfDay());
        LocalDateTime otherStartTime = other.dateTime;
        return (otherStartTime.isAfter(dateTime) || otherStartTime.isEqual(dateTime))
                && otherStartTime.isBefore(endTime);
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
