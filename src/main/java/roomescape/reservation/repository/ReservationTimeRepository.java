package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.payload.ReservationTimeRequest;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTimeRequest request);

    List<ReservationTime> findAll();

    void deleteById(Long id);

}
