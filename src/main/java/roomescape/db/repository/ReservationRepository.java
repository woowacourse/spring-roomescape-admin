package roomescape.db.repository;

import java.util.List;
import roomescape.db.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    void save(final Reservation reservation);

    void delete(final long id);

    long getCurrentId();
}
