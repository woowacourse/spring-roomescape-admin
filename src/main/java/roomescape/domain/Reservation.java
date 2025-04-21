package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(@JsonProperty("id") final long id,
                       @JsonProperty("name") final String name,
                       @JsonProperty("date") final LocalDate date,
                       @JsonProperty("time") final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        this(0, name, date, time);
    }

    public Reservation withId(final long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public boolean isSameDateTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
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

    public LocalTime getTime() {
        return time;
    }
}
