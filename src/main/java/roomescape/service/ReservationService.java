package roomescape.service;

import java.util.List;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationCreateResponse create(ReservationCreateRequest reservationCreateRequest);

    List<ReservationResponse> findAll();

    void delete(final Long id);
}
