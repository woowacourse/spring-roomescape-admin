package roomescape.repository;

import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.ReservationTime;

public record ReservationTimeRegisterDetail(String startAt) {

    public ReservationTimeRegisterDetail(ReservationTimeRequest request) {
        this(request.startAt());
    }

    public ReservationTime toEntity(long timeId) {
        return new ReservationTime(timeId, startAt);
    }
}
