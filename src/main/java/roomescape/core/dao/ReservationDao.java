package roomescape.core.dao;

import java.util.List;
import roomescape.web.domain.Reservation;

public interface ReservationDao {

    Reservation save(Reservation reservation);

    List<Reservation> findAllReservations();

    void delete(Long id);
}
