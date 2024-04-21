package roomescape.mapper;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Component
public class ReservationMapper {

    public ReservationTime mapReservationTimeRequestToEntity(ReservationTimeRequest timeRequest) {
        return new ReservationTime(null, timeRequest.startAt());
    }

    public Reservation mapReservationRequestToEntity(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = new ReservationTime(reservationRequest.timeId(), null);
        return new Reservation(null, reservationRequest.name(), reservationRequest.date(), reservationTime);
    }

    public ReservationTimeResponse mapReservationTimeToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    public ReservationResponse mapReservationToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                mapReservationTimeToResponse(reservation.getTime())
        );
    }
}
