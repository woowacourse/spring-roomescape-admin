package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    List<ReservationTime> findReservationTimes();

    Long createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest);

    Optional<ReservationTime> findReservationTimeById(Long createdReservationTimeId);

    void deleteReservationTimeById(Long id);
}
