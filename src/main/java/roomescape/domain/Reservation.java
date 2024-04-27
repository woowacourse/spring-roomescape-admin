package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        validateId(id);
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(String name, LocalDate date, ReservationTime reservationTime) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] id값이 존재하지 않습니다.");
        }
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
