package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;

public interface ReservationTimeService {

    List<ReservationTimeResponse> getAll();

    ReservationTimeResponse save(ReservationTimeRequest request);

    void delete(Long id);
}
