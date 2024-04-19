package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationDao {
    List<Reservation> findAllReservations();

    Long insert(ReservationRequest reservationRequest);

    void delete(Long id);
}
