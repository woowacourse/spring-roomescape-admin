package roomescape.reservation.entity;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.time.entity.ReservationTime;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createNew(String name, LocalDate date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }

    public static Reservation of(long id, String name, LocalDate date, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public Reservation withId(long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

}
