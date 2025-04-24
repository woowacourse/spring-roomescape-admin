package roomescape.reservationtime.dto.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.common.domain.Cacheable;
import roomescape.reservationtime.domain.ReservationTime;

public interface ReservationTimeRepository {
    List<ReservationTime> getAll();

    ReservationTime put(ReservationTime reservationTime);

    void deleteById(long id);

    Optional<ReservationTime> findById(long id);

    boolean checkExistsByStartAt(LocalTime time);

    Long getCachedId(Cacheable domain);

    void cacheId(Cacheable domain, Long id);
}
