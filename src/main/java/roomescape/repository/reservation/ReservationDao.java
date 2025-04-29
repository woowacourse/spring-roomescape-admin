package roomescape.repository.reservation;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationDao {

    long save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(final Long id);
}
