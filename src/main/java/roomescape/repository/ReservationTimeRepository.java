package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.request.ReservationTimeRequest;
import roomescape.response.ReservationTimeResponse;

import java.util.List;

@Repository
public interface ReservationTimeRepository {
    ReservationTimeResponse addTime(ReservationTimeRequest request);

    List<ReservationTimeResponse> findAllReservationTimes();

    void deleteTime(Long id);

    ReservationTime findById(Long id);
}
