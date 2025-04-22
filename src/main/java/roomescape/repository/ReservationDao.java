package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationDao {

    void save(Reservation reservation);

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    void deleteById(long id);
}
