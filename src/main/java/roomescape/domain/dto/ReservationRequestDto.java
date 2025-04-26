package roomescape.domain.dto;

import java.time.LocalDate;
import roomescape.domain.ReservationTime;

public record ReservationRequestDto(String name, LocalDate date, Long reservationTimeId) {
}
