package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationJoinDto(
        Long reservationId,
        String name,
        LocalDate date,
        Long timeId,
        LocalTime startAt
) {
}
