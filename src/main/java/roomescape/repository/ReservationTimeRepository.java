package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    long add(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
