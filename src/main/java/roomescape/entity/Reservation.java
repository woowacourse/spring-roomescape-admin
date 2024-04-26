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
    private final GameTime gameTime;

    public Reservation(Long id, Name name, ReservationDate reservationDate, GameTime gameTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.gameTime = gameTime;
    }

    public Reservation(Long id, String name, LocalDate date, GameTime gameTime) {
        this(id, new Name(name), new ReservationDate(date), gameTime);
    }

    public Reservation(String name, LocalDate date, GameTime gameTime) {
        this(null, name, date, gameTime);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getName(), reservation.getStartDate(), reservation.gameTime);
    }

    public boolean isConflictWith(Reservation target) {
        LocalDateTime start = getStartDateTime();
        LocalDateTime end = getEndDateTime();
        LocalDateTime targetStart = target.getStartDateTime();
        LocalDateTime targetEnd = target.getEndDateTime();

        if (targetEnd.isBefore(start) || targetStart.isAfter(end)) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public long getGameTimeId() {
        return gameTime.getId();
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getStartDate() {
        return reservationDate.getDate();
    }

    public LocalTime getStartTime() {
        return gameTime.getStartAt();
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
                gameTime, that.gameTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, reservationDate, gameTime);
    }
}
