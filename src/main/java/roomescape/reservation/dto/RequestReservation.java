package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.time.ReservationTime;

public record RequestReservation(String name, LocalDate date, ReservationTime time) {
}
