package roomescape.repository;

import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import java.util.List;

public interface ReservationRepository {

    Long save(final ReservationCreateRequest createRequest);

    List<Reservation> findAll();

    void deleteById(final Long id);
}
