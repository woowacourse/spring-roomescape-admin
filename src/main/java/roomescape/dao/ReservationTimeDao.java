package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeDao {

    List<ReservationTime> selectAll();

    ReservationTime insert(ReservationTime reservationTime);

    void delete(Long id);

}
