package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = new Name(name);
        this.time = new ReservationTime(LocalDateTime.of(date, time));
    }

    public boolean isReservationTimeConflictWith(Reservation other) {
        return time.isConflictWith(other.time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getStartDate() {
        return time.getStartDate();
    }

    public LocalTime getStartTime() {
        return time.getStartTime();
    }
}
