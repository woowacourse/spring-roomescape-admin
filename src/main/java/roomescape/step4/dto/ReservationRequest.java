package roomescape.step4.dto;

import java.time.LocalDate;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
}
