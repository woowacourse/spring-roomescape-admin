package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private int id;
    private String name;
    private LocalDate localDate;
    private LocalTime localTime;

    public Reservation(int id, String name, LocalDate localDate, LocalTime localTime) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
