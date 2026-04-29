package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class Reservation {
    private final Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id,
            String name,
                       LocalDate date,
                       LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
