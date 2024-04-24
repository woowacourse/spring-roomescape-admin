package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    public static final int TIME_DURATION = 1;

    private final Long id;
    private final Name name;
    private final ReservationDate reservationDate;
    private final ReservationTime reservationTime;

    public Reservation(Long id, Name name, ReservationDate reservationDate, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, new Name(name), new ReservationDate(date), new ReservationTime(time));
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

    public LocalDate getStartDate() {
        return reservationDate.getDate();
    }

    public LocalTime getStartTime() {
        return reservationTime.getStartAt();
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(getStartDate(), getStartTime());
    }

    public LocalDateTime getEndDateTime() {
        return getStartDateTime().plusHours(TIME_DURATION);
    }
}
