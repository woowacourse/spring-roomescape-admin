package roomescape.usecase.Reservation;

import java.time.LocalDate;
import roomescape.enttity.ReservationTime.ReservationTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        validateNotNull(name, date, reservationTime);
        validateNameLength(name);
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private void validateNotNull(final String name, final LocalDate date, final ReservationTime reservationTime) {
        if (name == null || date == null || reservationTime == null) {
            throw new IllegalArgumentException("모든 값들이 존재해야 합니다.");
        }
    }

    private void validateNameLength(final String name) {
        if (name.length() > 5) {
            throw new IllegalArgumentException("이름은 5자를 넘길 수 없습니다.");
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
