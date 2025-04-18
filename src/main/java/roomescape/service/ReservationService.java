package roomescape.service;


import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationService {

    List<ReservationResponse> get();

    ReservationResponse create(ReservationRequest req);

    void delete(Long id);
}
