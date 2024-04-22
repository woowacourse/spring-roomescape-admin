package roomescape.dto.response;

import roomescape.domain.ReservationTime;

public record ResponseTimes(Long id, String startAt) {

    public ResponseTimes(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
