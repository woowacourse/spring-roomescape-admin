package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date; // THINK change LocalDateTime??
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameId(Long id) {
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

    public ReservationTime getTime() {
        return time;
    }

    public Reservation toEntity(Long id) {
        return new Reservation(
                id,
                this.getName(),
                this.date,
                this.time
        );
    }

}
