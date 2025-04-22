package roomescape.domain.ReservationTime;

import java.util.List;
import roomescape.dto.request.ReservationTimeCreateRequest;

public interface ReservationTimes {

    List<ReservationTime> findAll();

    long create(ReservationTimeCreateRequest reservationTimeCreateRequest);

    void delete(final Long id);

    ReservationTime findById(Long id);
}
