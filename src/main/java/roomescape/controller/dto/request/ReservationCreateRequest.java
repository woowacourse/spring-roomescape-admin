package roomescape.controller.dto.request;

import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationCreate;

import java.time.LocalDate;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationCreate toData(final ReservationTime time) {
        return new ReservationCreate(name, date, time);
    }
}
