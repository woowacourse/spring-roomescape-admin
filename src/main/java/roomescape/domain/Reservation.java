package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation implements Comparable<Reservation> {
    private long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(String name, LocalDateTime dateTime) {
        this(0, name, dateTime);
    }

    public Reservation(long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    @Override
    public int compareTo(Reservation other) {
        return dateTime.compareTo(other.dateTime);
    }

    public boolean hasSameId(long id) {
        return this.id == id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setId(long id) {
        this.id = id;
    }
}
