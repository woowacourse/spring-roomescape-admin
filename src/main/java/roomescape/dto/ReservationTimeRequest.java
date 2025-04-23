package roomescape.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.ReservationTime;

public record ReservationTimeRequest(
        String startAt
) {
    public ReservationTime toEntity(Long id) {
        return new ReservationTime(
                id,
                LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm"))
                );
    }
}
