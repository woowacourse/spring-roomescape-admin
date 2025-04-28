package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationDao {
    Long save(final Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);

    Optional<Reservation> findById(Long id);
}
