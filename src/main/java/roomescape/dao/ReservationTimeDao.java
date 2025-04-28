package roomescape.dao;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeDao {

    ReservationTime addReservation(ReservationTime reservationTime);
    List<ReservationTime> getTimeReservations();
    int deleteTimeReservation(Long id);
    ReservationTime findTimeById(Long reservationTimeId);

}
