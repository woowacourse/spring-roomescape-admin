package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation() {
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
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

    public Time getTime() {
        return time;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
