package roomescape.domain.reservations.infrastructure;

import java.util.List;
import java.util.Optional;
import roomescape.domain.reservations.entity.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    void deleteById(Long id);
}
