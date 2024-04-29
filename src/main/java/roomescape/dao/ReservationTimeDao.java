package roomescape.dao;

import roomescape.dto.ReservationTimeSaveDto;
import roomescape.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTimeSaveDto reservationTimeDto);

    void delete(long id);
}
