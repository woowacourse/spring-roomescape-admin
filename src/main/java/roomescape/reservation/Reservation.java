package roomescape.reservation;

import java.time.LocalDate;

import roomescape.time.ReservationTime;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {
}
