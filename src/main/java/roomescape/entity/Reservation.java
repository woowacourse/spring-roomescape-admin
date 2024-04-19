package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate reservationStartDate, LocalTime reservationStartTime) {
        this.id = id;
        this.name = new Name(name);
        this.time = new ReservationTime(LocalDateTime.of(reservationStartDate, reservationStartTime));
    }

    public Reservation(String name, LocalDate reservationStartDate, LocalTime reservationStartTime) {
        this(null, name, reservationStartDate, reservationStartTime);
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
