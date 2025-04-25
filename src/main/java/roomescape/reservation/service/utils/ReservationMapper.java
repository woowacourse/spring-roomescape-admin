package roomescape.reservation.service.utils;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.domain.ReservationTime;
import roomescape.time.service.utils.ReservationTimeMapper;

@Component
public class ReservationMapper {

    private final ReservationTimeMapper reservationTimeMapper;

    @Autowired
    public ReservationMapper(ReservationTimeMapper reservationTimeMapper) {
        this.reservationTimeMapper = reservationTimeMapper;
    }

    public Reservation toReservation(ReservationRequest reservationRequest, ReservationTime reservationTime) {
        return new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTime
        );
    }

    public ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTimeMapper.toTimeResponse(reservation.getReservationTime())
        );
    }

    public List<ReservationResponse> toReservationResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::toReservationResponse)
                .toList();
    }
}
