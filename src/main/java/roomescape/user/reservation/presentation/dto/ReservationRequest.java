package roomescape.user.reservation.presentation.dto;

import java.time.LocalDate;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {

}
