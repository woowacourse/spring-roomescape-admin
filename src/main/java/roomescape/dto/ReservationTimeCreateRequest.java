package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(LocalTime startAt) {
    public ReservationTime toTime(long id) {
        return new ReservationTime(id, startAt);
    }
}
