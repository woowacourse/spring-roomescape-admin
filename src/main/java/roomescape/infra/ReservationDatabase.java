package roomescape.infra;

import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.util.List;

public interface ReservationDatabase {

    List<Reservation> findAll();

    Reservation findById(long id);

    long saveAndGetId(final ReservationCreateRequest request);

    void deleteById(final long id);
}
