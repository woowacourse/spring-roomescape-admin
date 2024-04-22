package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    List<ReservationTime> findReservationTimes();

    Optional<ReservationTime> findReservationTimeById(Long id);

    Long createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest);

    void deleteReservationTimeById(Long id);
}
