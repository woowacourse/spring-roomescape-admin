package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationRequest;

@Repository
public interface ReservationRepository {
    long save(Reservation reservation);
}
