package roomescape.repository;

import java.util.Map;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    Long add(Reservation reservation);

    void remove(Long id);

    Map<Long, Reservation> findAllWithId();
}
