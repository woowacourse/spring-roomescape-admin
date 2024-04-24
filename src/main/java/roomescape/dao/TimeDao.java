package roomescape.dao;

import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

public interface TimeDao {
    ReservationTime add(ReservationTimeAddRequest request);
}
