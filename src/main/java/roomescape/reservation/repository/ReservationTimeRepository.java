package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.payload.ReservationTimeRequest;

public interface ReservationTimeRepository {

    Long save(ReservationTimeRequest request);

    Optional<ReservationTime> findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);

}
