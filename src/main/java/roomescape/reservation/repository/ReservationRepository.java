package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> getAll();

    Reservation put(Reservation item);

    void deleteById(long id);

    Optional<Reservation> findById(long id);

    Long getCachedId(Reservation reservation);

    void cacheId(Reservation reservation, Long id);

    void clearAllCachedIds();
}
