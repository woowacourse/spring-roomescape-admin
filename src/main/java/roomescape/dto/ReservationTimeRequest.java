package roomescape.dto;

import roomescape.entity.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toEntity(long id) {
        return new ReservationTime(id, startAt);
    }
}
