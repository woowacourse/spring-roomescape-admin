package roomescape.service.dto.request;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationData(
        String name,
        LocalDate date,
        ReservationTime time
) {
}
