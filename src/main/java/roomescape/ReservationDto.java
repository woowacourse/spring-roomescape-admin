package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationDto(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
