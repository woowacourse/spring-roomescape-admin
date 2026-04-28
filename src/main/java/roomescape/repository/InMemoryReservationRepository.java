package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAllReservations() {
        return new ArrayList<>(reservations);
    }
}
