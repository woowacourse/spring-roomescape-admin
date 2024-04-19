package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationDao {
    List<Reservation> findAll();

    long save(ReservationRequest reservationRequest);

    boolean existsById(long id);

    void deleteById(long id);
}
