package roomescape.domain;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(Reservation reservation);

    int deleteById(long id);

    void deleteAll();
}
