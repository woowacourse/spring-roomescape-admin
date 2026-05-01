package roomescape.domain.reservationtime.dto;

import roomescape.domain.reservationtime.ReservationTime;

public record CreateTimeRequest(
    String startAt
) {

    public void validate() {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }

    public ReservationTime toEntity() {
        return ReservationTime.createWithoutId(startAt);
    }
}
