package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationService {
    List<ReservationResponse> findAll();

    ReservationResponse save(ReservationRequest reservationRequest);

    boolean deleteById(long id);
}
