package roomescape.reservation;

import java.time.LocalDate;

import roomescape.time.ReservationTime;

public record Reservation(long id, String name, LocalDate date, ReservationTime time) {
    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(0, name, date, time);
    }
}
