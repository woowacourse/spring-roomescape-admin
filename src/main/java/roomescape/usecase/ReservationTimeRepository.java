package roomescape.usecase;

import roomescape.enttity.ReservationTime;

public interface ReservationTimeRepository {
    CreateReservationTimeOutput addReservationTime(ReservationTime reservationTime);

    GetReservationTimeOutput getReservationTime();
}
