package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private final LocalDate date;
    private final String name;
    private final LocalTime time;

    public ReservationRequest(LocalDate date, String name, LocalTime time) {
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
