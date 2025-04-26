package roomescape.user.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationV1Request(
        String name,
        LocalDate date,
        LocalTime time
) {

}
