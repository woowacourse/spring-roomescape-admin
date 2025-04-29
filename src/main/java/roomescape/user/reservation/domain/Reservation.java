package roomescape.user.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        validateNotNull(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNull(final String name, final LocalDate date, final ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
    }

    public Long extractTimeId() {
        return time.getId();
    }

    public LocalTime extractTime() {
        return time.getStartAt();
    }
}
