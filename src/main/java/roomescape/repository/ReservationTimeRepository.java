package roomescape.repository;

import roomescape.domain.ReservationTime;
import java.util.List;

public interface ReservationTimeRepository {

    Long save(final ReservationTime reservationTime);

    List<ReservationTime> findAll();

    ReservationTime findById(final Long id);

    void deleteById(final Long id);
}
