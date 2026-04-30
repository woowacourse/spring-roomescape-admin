package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;

public interface TimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(TimeRequest timeRequest);

    void deleteById(long id);
}
