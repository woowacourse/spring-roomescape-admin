package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {
    public ReservationTime toDomain(long id) {
        return new ReservationTime(id, startAt);
    }
}
