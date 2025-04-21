package roomescape.infra;

import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.util.List;

public interface ReservationTimeDatabase {

    List<ReservationTime> findAll();

    ReservationTime findById(final long id);

    long saveAndGetId(final ReservationTimeCreateRequest request);

    void deleteById(final long id);
}
