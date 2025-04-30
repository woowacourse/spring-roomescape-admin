package roomescape.dto.reservation;

import java.time.LocalDate;

public record ReservationCreateRequest(
    String name,
    LocalDate date,
    long timeId
) {

}
