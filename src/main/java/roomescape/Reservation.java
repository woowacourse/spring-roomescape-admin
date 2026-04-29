package roomescape;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    private Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.toString();
    }

    public ReservationTime getTime() {
        return time;
    }
}
