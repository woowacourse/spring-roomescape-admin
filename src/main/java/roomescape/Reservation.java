package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
