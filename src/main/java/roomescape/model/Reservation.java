package roomescape.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final ReservationTime time;

    public Reservation(long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
