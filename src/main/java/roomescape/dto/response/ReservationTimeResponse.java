package roomescape.dto.response;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public static ReservationTimeResponse of(final long id, final ReservationTime reservationTime) {
        return new ReservationTimeResponse(id, reservationTime.getStartAt());
    }

    public static List<ReservationTimeResponse> from(final List<ReservationTime> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationTimeResponse(reservation.getId(), reservation.getStartAt()))
                .toList();
    }
}
