package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.time.ReservationTime;

public record ResponseReservation(Long id, String name, LocalDate date, ReservationTime time) {
}
