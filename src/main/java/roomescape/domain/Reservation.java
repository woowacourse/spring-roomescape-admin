package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final User user;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, User user, LocalDate date, LocalTime time) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.time = time;
    }
}
