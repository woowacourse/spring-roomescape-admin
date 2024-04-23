package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    Reservation createReservation(ReservationRequest reservationRequest);

    boolean deleteReservation(Long id);
}
