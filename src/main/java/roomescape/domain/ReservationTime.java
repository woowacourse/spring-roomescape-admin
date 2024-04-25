package roomescape.domain;

import roomescape.dto.ReservationTimeRequest;

public record ReservationTime(Long id, String startAt) {

    public static ReservationTime toEntity(Long id, ReservationTimeRequest request) {
        return new ReservationTime(id, request.getStartAt());
    }
}
