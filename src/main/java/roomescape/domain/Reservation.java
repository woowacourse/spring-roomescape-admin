package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final LocalTime time;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, String time) {
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public boolean hasId(Long id) {
        return this.id.equals(id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
