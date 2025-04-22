package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequestDto(String name, LocalDate date, Long timeId) {

}
