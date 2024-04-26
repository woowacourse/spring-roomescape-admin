package roomescape.db;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    Reservation save(final Reservation reservation);

    boolean isTimeIdExist(final long id);

    List<Reservation> findAll();

    void deleteById(final long id);
}
