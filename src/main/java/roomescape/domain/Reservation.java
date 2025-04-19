package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDateTime dateTime;

    public Reservation(final Long id, final ReservationName name, final ReservationDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.getValue();
    }

    public LocalDate date() {
        return dateTime.date();
    }

    public LocalTime time() {
        return dateTime.time();
    }

    public boolean equalsById(Long id) {
        return Objects.equals(this.id, id);
    }
}
