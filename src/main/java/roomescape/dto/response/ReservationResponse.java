package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {

    public static List<ReservationResponse> from(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTime()));
    }

    private record ReservationTimeResponse(long id, LocalTime startAt) {

        private static ReservationTimeResponse from(final ReservationTime reservationTime) {
            return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
        }
    }
}
