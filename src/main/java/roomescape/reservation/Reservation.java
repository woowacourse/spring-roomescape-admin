package roomescape.reservation;

import java.time.LocalDate;

import roomescape.time.ReservationTime;

public record Reservation(long id, String name, LocalDate date, ReservationTime time) {
}
