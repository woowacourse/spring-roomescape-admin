package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime localTime;

    public Reservation(Long id, String name, LocalDate date, LocalTime localTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.localTime = localTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
