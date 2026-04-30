package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface TimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTimeRequest reservationTimeRequest);

    void deleteById(long id);
}
