package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationTime.ReservationTime;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {
}
