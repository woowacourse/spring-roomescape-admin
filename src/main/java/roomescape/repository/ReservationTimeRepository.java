package roomescape.repository;

import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import java.util.List;

public interface ReservationTimeRepository {

    Long save(final ReservationTimeCreateRequest createRequest);

    List<ReservationTime> findAll();
}
