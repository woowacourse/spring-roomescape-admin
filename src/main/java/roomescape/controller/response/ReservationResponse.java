package roomescape.controller.response;

import java.time.LocalDate;

public record ReservationResponse(

        Long id,

        String name,

        LocalDate date,

        ReservationTimeResponse time
) {
}
