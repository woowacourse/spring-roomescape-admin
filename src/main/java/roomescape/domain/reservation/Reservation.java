package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private boolean deleted;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createWithoutId(String name, LocalDate date, LocalTime time) {
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

    public void delete() {
        deleted = true;
    }
}
