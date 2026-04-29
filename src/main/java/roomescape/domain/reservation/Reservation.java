package roomescape.domain.reservation;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.domain.reservationtime.ReservationTime;

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

    public static Reservation createWithoutId(String name, LocalDate date, ReservationTime time) {
        return new Reservation(
            null,
            name,
            date,
            time
        );
    }

    public static Reservation createWithId(long id, Reservation reservation) {
        return new Reservation(
            id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }

    public static Reservation of(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
    ) {
        return new Reservation(id, name, date, time);
    }
}
