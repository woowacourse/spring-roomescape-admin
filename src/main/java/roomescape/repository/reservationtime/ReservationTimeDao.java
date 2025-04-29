package roomescape.repository.reservationtime;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeDao {

    long save(final ReservationTime time);

    List<ReservationTime> findAll();

    void deleteById(final Long id);

    ReservationTime findById(final Long id);
}
