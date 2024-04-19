package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();

    void save(Reservation reservation);

    boolean deleteById(long id);
}
