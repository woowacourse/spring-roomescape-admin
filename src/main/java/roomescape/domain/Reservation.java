package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(final long id,
                       final String name,
                       final LocalDate date,
                       final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
