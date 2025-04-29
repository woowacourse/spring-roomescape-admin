package roomescape.user.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationV1Response(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

}
