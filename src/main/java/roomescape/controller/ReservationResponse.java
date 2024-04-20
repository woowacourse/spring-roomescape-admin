package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservationResponse(

        Long id,

        String name,

        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
