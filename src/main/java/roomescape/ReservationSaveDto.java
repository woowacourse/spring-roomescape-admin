package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSaveDto(String name, LocalDate reservationDate, LocalTime reservationTime) {
}
