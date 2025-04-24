package roomescape.repository.reservation;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    Reservation add(Reservation reservation);

    int deleteBy(Long id);

    List<Reservation> findAll();
}
