package roomescape.reservation.repository;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
@AllArgsConstructor
public class ReservationRepository {
    private final List<Reservation> reservations;

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
