package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate reservationDate = reservation.getReservationDate();
        ReservationTime reservationTime = reservation.getReservationTime();
        return new ReservationResponse(
                reservation.getId(),
                name.asText(),
                reservationDate.getDate(),
                ReservationTimeResponse.from(reservationTime)
        );
    }
}
