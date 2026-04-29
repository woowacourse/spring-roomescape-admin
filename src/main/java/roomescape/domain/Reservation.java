package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Name name;
    private LocalDate date;
    private Long timeId;

    public Reservation() {
    }

    public Reservation(Long id, Name name, LocalDate date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation(Name name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.timeId);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
