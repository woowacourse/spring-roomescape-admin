package roomescape.mapper;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;

public class ReservationMapper {

    public ReservationResponse mapToResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation mapToReservation(Long id, ReservationSaveRequest request) {
        return new Reservation(id, request.name(), request.date(), request.time());
    }
}
