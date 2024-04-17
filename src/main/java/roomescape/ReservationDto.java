package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    private LocalDate date;
    private String name;
    private LocalTime time;

    public ReservationDto() {

    }

    public ReservationDto(LocalDate date, String name, LocalTime time) {
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
