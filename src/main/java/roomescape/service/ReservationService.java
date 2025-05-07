package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationService {

    List<ReservationResponse> findAllReservations();

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    int deleteReservationById(Long id);

    boolean existsById(Long id);
}
