package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean hasSameId(final Long id) {
        return Objects.equals(this.id, id);
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

    public LocalTime getTime() {
        return time;
    }
}
