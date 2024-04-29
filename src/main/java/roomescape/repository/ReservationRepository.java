package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservationRequest);

    List<Reservation> findAll();

    Optional<Integer> deleteById(Long id);
}
