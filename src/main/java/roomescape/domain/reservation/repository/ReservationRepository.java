package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {
    Reservation save(ReservationRequest reservationRequest);

    List<Reservation> findAll();

    void deleteById(Long id);
}
