package roomescape.reservation.entity;

import java.time.LocalDateTime;

public class Reservation {

    private long id;
    private String name;
    private LocalDateTime dateTime;

    public Reservation(long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public boolean isSameId(long id) {
        return this.id == id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
