package roomescape.service;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeService {

    ReservationTime save(String startAt);

    List<ReservationTime> findAll();

    void deleteById(Long targetId);
}
