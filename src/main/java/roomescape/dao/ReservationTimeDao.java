package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {

    Optional<ReservationTime> select(Long id);

    List<ReservationTime> selectAll();

    ReservationTime insert(ReservationTime reservationTime);

    boolean delete(Long id);

}
