package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long ld;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long ld, String name, LocalDate date, LocalTime time) {
        this.ld = ld;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getLd() {
        return ld;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
