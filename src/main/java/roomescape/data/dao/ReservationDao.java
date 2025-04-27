package roomescape.data.dao;

import java.util.List;
import roomescape.business.domain.Reservation;

public interface ReservationDao {

    Long save(Reservation reservation);

    List<Reservation> findAll();

    boolean remove(final Long id);
}
