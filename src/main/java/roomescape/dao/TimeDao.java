package roomescape.dao;

import java.time.LocalTime;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

public interface TimeDao {
    ReservationTime add(ReservationTimeAddRequest request);

    boolean isExist(LocalTime time);
}
