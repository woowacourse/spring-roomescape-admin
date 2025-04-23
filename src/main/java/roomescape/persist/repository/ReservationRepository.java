package roomescape.persist.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    void removeById(long id);
}
