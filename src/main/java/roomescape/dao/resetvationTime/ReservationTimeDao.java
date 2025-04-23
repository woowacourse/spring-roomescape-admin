package roomescape.dao.resetvationTime;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    long create(ReservationTimeCreateRequest reservationTimeCreateRequest);

    void delete(final Long id);

    ReservationTime findById(Long id);
}
