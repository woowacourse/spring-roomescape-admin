package roomescape.dao;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    long insert(ReservationTimeRequest reservationTimeRequest);

    void deleteById(long timeId);

    ReservationTime findById(long timeId);
}
