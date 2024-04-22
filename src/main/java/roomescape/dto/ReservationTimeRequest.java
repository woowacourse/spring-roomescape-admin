package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toTime() {
        return new ReservationTime(null, startAt);
    }
}
