package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
    LocalDate date,
    String name,
    LocalTime time
) {

}
