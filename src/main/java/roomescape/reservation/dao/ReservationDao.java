package roomescape.reservation.dao;

import java.util.List;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;

public interface ReservationDao {

    Reservation insert(ReservationRequest reservationRequest);

    Reservation findById(long id);

    List<Reservation> findAll();

    void delete(long id);
}
