package roomescape.reservation.utils;

import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Component
public class ReservationMapper {

    public Reservation toReservation(ReservationRequest reservationRequest) {
        return new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
    }

    public ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
