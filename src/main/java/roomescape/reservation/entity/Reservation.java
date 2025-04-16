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

    public Reservation(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public static Reservation toEntity(Long id, Reservation reservation) {
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDateTime());
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
