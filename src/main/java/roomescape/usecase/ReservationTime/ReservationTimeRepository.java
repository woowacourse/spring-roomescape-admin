package roomescape.usecase.ReservationTime;

import java.util.List;
import roomescape.enttity.ReservationTime.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTimeOutput addReservationTime(ReservationTime reservationTime);

    List<ReservationTimeOutput> getReservationTime();

    void deleteReservationTime(long id);
}
