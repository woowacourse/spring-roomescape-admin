package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this(id, new Name(name), new ReservationDate(date), reservationTime);
    }

    public Reservation(String name, LocalDate date, ReservationTime reservationTime) {
        this(null, name, date, reservationTime);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getName(), reservation.getStartDate(), reservation.reservationTime);
    }

    public long getId() {
        return id;
    }

    public long getReservationTimeId() {
        return reservationTime.getId();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(
                reservationTime, that.reservationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, reservationDate, reservationTime);
    }
}
