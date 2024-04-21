package roomescape.dto;

import roomescape.domain.ReservationTimeDto;

public record ResponseTimes(Long id, String startAt) {

    public ResponseTimes(ReservationTimeDto reservationTimeDto) {
        this(reservationTimeDto.getId(), reservationTimeDto.getStartAt());
    }
}
