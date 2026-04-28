package roomescape; // THINK domain? entity?

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date; // THINK change LocalDateTime??
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
