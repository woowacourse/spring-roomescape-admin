package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationAddRequest {
    private LocalDate date;
    private String name;
    private LocalTime time;

    public ReservationAddRequest() {

    }

    public ReservationAddRequest(LocalDate date, String name, LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }
}
