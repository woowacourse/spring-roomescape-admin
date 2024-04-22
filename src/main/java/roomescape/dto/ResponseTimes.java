package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ResponseTimes(Long id, String startAt) {

    public ResponseTimes(ReservationTime reservationTimeDto) {
        this(reservationTimeDto.getId(), reservationTimeDto.getStartAt());
    }
}
