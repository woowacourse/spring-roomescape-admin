package roomescape.dao;

import java.util.List;
import roomescape.entity.ReservationWithTimeId;
import roomescape.model.Reservation;

public interface ReservationDao {

    List<Reservation> selectAllReservation();

    Long addReservation(ReservationWithTimeId reservation);

    int deleteReservationById(Long id);

}
