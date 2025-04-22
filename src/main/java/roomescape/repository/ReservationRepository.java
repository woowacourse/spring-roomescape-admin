package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;

public interface ReservationRepository {

    List<ReservationResponse> findAll();

    Optional<ReservationResponse> findById(Long id);

    long add(ReservationRequest request);

    void deleteById(Long id);
}
