package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface RoomescapeTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime saveReservationTime(final ReservationTime reservationTime);

    int deleteById(final long id);

    void clear();
}
