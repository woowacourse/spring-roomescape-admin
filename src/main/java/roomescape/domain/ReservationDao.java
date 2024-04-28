package roomescape.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    List<Reservation> findAll();

    Optional<Reservation> findById(long id);

    long save(Reservation reservation);

    DeletedCount deleteById(long id);

    void deleteAll();
}
