package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

public interface ReservationTimeService {
    List<ReservationTimeResponse> findAll();

    ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest);

    boolean deleteById(long id);
}
