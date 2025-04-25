package roomescape.usecase.ReservationTime;

import java.util.List;
import roomescape.enttity.ReservationTime.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTimeOutput addReservationTime(ReservationTime reservationTime);

    List<ReservationTimeOutput> getAllReservationTimes();

    ReservationTime getReservationTime(Long timeId);

    void deleteReservationTime(long id);

    boolean existsById(long id);
}
