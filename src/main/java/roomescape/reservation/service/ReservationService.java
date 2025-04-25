package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

public interface ReservationService {

    List<ReservationResponse> getAll();

    ReservationResponse save(ReservationRequest request);

    void delete(Long id);
}
