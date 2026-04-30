package roomescape.domain.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,

        String name,

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate date,

        ReservationTimeResponse time
) {
}
