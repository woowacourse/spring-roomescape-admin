package roomescape.dao;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    void save(Reservation reservation);

    void delete(Reservation reservation);
}
