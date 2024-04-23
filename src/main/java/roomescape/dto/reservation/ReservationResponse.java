package roomescape.dto.reservation;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.reservationtime.ReservationTimeResponse;

public record ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {
    public static ReservationResponse from(Reservation reservation) { // todo 도메인 분리
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTimeId(), reservation.getTime())
        );
    }
}
