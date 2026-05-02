package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.controller.ReservationTimeRequest;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTimeRequest reservationTimeRequest);

    void deleteById(long id);
}
