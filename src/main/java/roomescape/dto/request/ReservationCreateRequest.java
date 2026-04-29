package roomescape.dto.request;

import roomescape.ReservationTime;
import roomescape.dto.ReservationData;

import java.time.LocalDate;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationData toData(final ReservationTime time) {
        return new ReservationData(name, date, time);
    }
}
