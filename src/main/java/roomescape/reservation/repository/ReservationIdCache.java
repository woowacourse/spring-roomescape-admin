package roomescape.reservation.repository;

import org.springframework.stereotype.Component;
import roomescape.common.repository.IdCache;
import roomescape.reservation.domain.Reservation;

@Component
public class ReservationIdCache extends IdCache<Reservation> {
}
