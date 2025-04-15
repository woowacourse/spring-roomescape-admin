package roomescape.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String userName;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String userName, LocalDate date, LocalTime time) {
        this.id = id;
        this.userName = userName;
        this.date = date;
        this.time = time;
    }
}
