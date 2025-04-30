package roomescape.repository.reservation;

import java.util.List;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;

public interface ReservationDao {

    void save(Reservation reservation);

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    boolean deleteById(long id);
}
