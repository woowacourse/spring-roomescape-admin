package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
