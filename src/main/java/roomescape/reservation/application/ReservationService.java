package roomescape.reservation.application;

import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.reservation.ui.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    List<ReservationResponse> getReservations();

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    void delete(long id);
}
