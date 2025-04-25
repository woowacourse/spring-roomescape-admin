package roomescape.usecase;

import java.util.List;
import roomescape.enttity.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTimeOutput addReservationTime(ReservationTime reservationTime);

    List<ReservationTimeOutput> getReservationTime();
}
