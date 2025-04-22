package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.controller.request.ReservationRequest;
import roomescape.reservation.controller.response.ReservationResponse;

public interface ReservationRepository {

    List<ReservationResponse> findAll();

    ReservationResponse findById(Long id);

    long add(ReservationRequest request);

    void deleteById(Long id);
}
