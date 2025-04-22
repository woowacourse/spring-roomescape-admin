package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import roomescape.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(final LocalTime startAt);

    List<ReservationTime> findAll();

    void delete(final Long id);
}
