package roomescape.service;

import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    List<ReservationResponse> getReservations();

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    void delete(long id);
}
