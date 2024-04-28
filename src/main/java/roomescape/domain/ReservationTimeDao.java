package roomescape.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(long id);

    long save(ReservationTime reservationTime);

    DeletedCount deleteById(long id);

    void deleteAll();
}
