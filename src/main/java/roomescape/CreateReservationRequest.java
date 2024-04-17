package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {
}
