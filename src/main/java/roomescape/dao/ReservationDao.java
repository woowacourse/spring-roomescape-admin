package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationDao {

    List<Reservation> findAll();

    long insert(ReservationRequest reservationRequest);

    void deleteById(long reservationId);

    Reservation findById(long reservationId);
}
