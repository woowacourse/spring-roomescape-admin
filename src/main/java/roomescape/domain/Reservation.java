package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private final Long id;
    private final Name name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation(Long id, Name name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
