package roomescape.user.reservationtime.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    Long save(final ReservationTime reservationTime);

    Optional<ReservationTime> findById(final Long id);

    List<ReservationTime> findAll();

    void deleteById(final Long id);
}
