package roomescape.persistence;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();
}
