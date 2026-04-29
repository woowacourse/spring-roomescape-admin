package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationtime.ReservationTime;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {
}
