package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestReservation(String name, LocalDate date, LocalTime time) {
}
