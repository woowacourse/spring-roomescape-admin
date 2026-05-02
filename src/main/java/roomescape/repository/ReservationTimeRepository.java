package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.request.ReservationTimeRequest;

import java.util.List;

@Repository
public interface ReservationTimeRepository {
    ReservationTime addTime(ReservationTime reservationTime);

    List<ReservationTime> findAllReservationTimes();

    void deleteTime(Long id);

    ReservationTime findById(Long id);
}
