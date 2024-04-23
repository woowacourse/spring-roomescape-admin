package roomescape.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    Optional<ReservationTime> findById(long id);

    ReservationTime create(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(long id);
}
