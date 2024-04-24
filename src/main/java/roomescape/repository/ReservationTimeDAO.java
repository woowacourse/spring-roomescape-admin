package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeDAO {
    List<ReservationTime> selectAllReservationTimes();

    ReservationTime selectReservationById(long id);

    ReservationTime insertReservationTime(ReservationTime reservationTime);

    void deleteReservationTime(long id);
}
