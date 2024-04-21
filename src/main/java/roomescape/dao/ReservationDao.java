package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAllReservations();

    Long insert(Reservation reservation);

    void delete(Long id);
}
