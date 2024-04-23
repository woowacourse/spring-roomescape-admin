package roomescape.dto.response;

import roomescape.domain.ReservationTime;

public record TimesResponse(Long id, String startAt) {

    public TimesResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
