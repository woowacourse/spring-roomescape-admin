package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(long id, String name, LocalDate date, LocalTime time) {
}
