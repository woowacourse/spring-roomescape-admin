package roomescape.dao;

import java.util.List;
import roomescape.entity.ReservationTime;

public interface ReservationTimeDaoInterface {

    ReservationTime addReservation(ReservationTime reservationTime);
    List<ReservationTime> getTimeReservations();
    int deleteTimeReservation(Long id);
    ReservationTime findTimeById(Long reservationTimeId);

}
