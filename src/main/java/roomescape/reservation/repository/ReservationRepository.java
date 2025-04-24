package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.common.domain.Cacheable;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> getAll();

    Reservation put(Reservation reservation);

    void deleteById(long id);

    Optional<Reservation> findById(long id);

    Long getCachedId(Cacheable domain);

    void cacheId(Cacheable domain, Long id);
}
