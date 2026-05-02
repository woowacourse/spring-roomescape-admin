package roomescape.domain;

import lombok.Getter;
import roomescape.domain.dto.ReservationCreateCommand;

import java.time.LocalDate;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(ReservationCreateCommand data) {
        return new Reservation(
                null,
                data.name(),
                data.date(),
                data.time()
        );
    }

    public Reservation saved(final Long id) {
        return new Reservation(
                id,
                name,
                date,
                time
        );
    }

    public static Reservation restore(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        return new Reservation(
                id,
                name,
                date,
                time
        );
    }
}
