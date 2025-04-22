package roomescape.infra;

import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDatabase {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(final long id);

    long saveAndGetId(final ReservationTimeCreateRequest request);

    void deleteById(final long id);
}
