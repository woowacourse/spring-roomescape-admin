package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReservationDate reservationDate;
    private final TimeSlot timeSlot;

    private Reservation(Long id, Name name, ReservationDate reservationDate, TimeSlot timeSlot) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.timeSlot = timeSlot;
    }

    public Reservation(Long id, String name, LocalDate date, TimeSlot timeSlot) {
        this(id, new Name(name), new ReservationDate(date), timeSlot);
    }

    public Reservation(Long id, String name, String date, TimeSlot timeSlot) {
        this(id, new Name(name), new ReservationDate(date), timeSlot);
    }

    public Reservation(String name, String date, TimeSlot timeSlot) {
        this(null, name, date, timeSlot);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReservationDate getReservationDate() {
        return reservationDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public LocalDateTime getReservationDateTime() {
        return LocalDateTime.of(reservationDate.getDate(), timeSlot.getTime());
    }

    public Reservation withId(long id) {
        return new Reservation(id, name, reservationDate, timeSlot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name=" + name +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + timeSlot +
                '}';
    }
}
