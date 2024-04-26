package roomescape.dao;

import roomescape.domain.Reservation;
import java.util.List;

public interface ReservationDao {

    Long save(final Reservation reservation);

    List<Reservation> findAll();

    void deleteById(final Long id);
}
