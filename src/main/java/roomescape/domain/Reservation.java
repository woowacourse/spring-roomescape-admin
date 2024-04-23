package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation implements Comparable<Reservation> {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(long id, Reservation reservationBeforeSave) {
        this(id, reservationBeforeSave.name, reservationBeforeSave.date, reservationBeforeSave.time);
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.date = dateTime.toLocalDate();
        this.time = new ReservationTime(dateTime.toLocalTime());
    }

    @Override
    public int compareTo(Reservation other) {
        LocalDateTime dateTime = LocalDateTime.of(date, time.getStartAt());
        LocalDateTime otherDateTime = LocalDateTime.of(other.date, other.time.getStartAt());
        return dateTime.compareTo(otherDateTime);
    }

    public boolean hasSameId(long id) {
        return this.id == id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time.getStartAt();
    }

    public ReservationTime getReservationTime() {
        return time;
    }
}
