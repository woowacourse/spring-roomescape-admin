package roomescape.service.dto;

import static roomescape.service.dto.ReservationTimeResponse.FORMATTER;

import roomescape.domain.ReservationTime;

public record AvailableReservationTimeResponse(long id, String startAt, boolean alreadyBooked) {

    public AvailableReservationTimeResponse(ReservationTime time, boolean alreadyBooked) {
        this(time.getId(), time.getStartAt().format(FORMATTER), alreadyBooked);
    }
}
