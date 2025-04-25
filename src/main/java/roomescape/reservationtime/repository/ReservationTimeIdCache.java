package roomescape.reservationtime.repository;

import org.springframework.stereotype.Component;
import roomescape.common.repository.IdCache;
import roomescape.reservationtime.domain.ReservationTime;

@Component
public class ReservationTimeIdCache extends IdCache<ReservationTime> {
}
