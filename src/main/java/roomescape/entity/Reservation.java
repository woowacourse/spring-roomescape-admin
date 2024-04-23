package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate reservationDate, LocalTime reservationStartTime) {
        this.id = id;
        this.name = new Name(name);
        this.time = new ReservationTime(LocalDateTime.of(reservationDate, reservationStartTime));
    }

    public Reservation(String name, LocalDate reservationDate, LocalTime reservationStartTime) {
        this(null, name, reservationDate, reservationStartTime);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDateTime getStartDateTime() {
        return time.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return time.getEndDateTime();
    }

    public LocalDate getStartDate() {
        return time.getStartDate();
    }

    public LocalTime getStartTime() {
        return time.getStart();
    }
}
