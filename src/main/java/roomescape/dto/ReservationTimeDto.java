package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeDto(Long id, String startAt) {

    public ReservationTime toDomain() {
        return new ReservationTime(id, startAt);
    }
}
