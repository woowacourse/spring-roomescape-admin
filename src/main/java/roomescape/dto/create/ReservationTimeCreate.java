package roomescape.dto.create;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTimeCreate(
        String startAt
) {
    public ReservationTime toReservationTime(final long id) {
        return new ReservationTime(id, LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm")));
    }
}
