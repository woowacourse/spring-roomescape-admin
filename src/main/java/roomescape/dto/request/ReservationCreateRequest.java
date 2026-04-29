package roomescape.dto.request;

import roomescape.dto.ReservationData;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {

    public ReservationData toData() {
        return new ReservationData(name, date, time);
    }
}
