package roomescape.dao.resetvationTime;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime create(ReservationTime reservationTime);

    void delete(final Long id);

    ReservationTime findById(Long id);
}
