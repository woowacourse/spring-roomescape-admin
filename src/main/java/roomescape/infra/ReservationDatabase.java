package roomescape.infra;

import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.util.List;
import java.util.Optional;

public interface ReservationDatabase {

    List<Reservation> findAll();

    Optional<Reservation> findById(long id);

    long saveAndGetId(final ReservationCreateRequest request);

    void deleteById(final long id);
}
