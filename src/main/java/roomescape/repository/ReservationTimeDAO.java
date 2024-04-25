package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeDAO {
    List<ReservationTime> findAllReservationTimes();

    ReservationTime findReservationById(long id);

    ReservationTime addReservationTime(ReservationTime reservationTime);

    void deleteReservationTime(long id);
}
