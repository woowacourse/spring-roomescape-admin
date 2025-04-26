package roomescape.dto.read;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTimeRead(
        Long id,
        String startAt
) {
    public ReservationTime toTime() {
        return new ReservationTime(id, LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm")));
    }
}
