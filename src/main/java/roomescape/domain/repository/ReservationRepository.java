package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    boolean deleteReservation(Long id);
}
