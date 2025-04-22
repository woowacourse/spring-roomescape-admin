package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    long save(final Reservation reservation);
    List<Reservation> findAll();
    void deleteById(long id);
    Optional<Reservation> findById(long id);
}
