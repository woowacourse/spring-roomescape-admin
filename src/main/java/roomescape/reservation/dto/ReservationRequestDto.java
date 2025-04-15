package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(LocalDate date, String name, LocalTime time) {

}
