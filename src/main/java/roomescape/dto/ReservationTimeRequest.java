package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(0L, startAt);
    }
}
