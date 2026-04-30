package roomescape.dto;

import java.time.LocalDate;

public record ReservationSaveDto(
        String name,
        LocalDate date,
        Long timeId
) {
}
