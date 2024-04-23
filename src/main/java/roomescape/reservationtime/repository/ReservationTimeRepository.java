package roomescape.reservationtime.repository;

import java.util.List;
import roomescape.reservationtime.domain.NewReservationTime;

public interface ReservationTimeRepository {

    Long save(NewReservationTime newReservationTime);

    List<NewReservationTime> findAll();
}
