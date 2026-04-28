package roomescape.infra;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void delete(Long id) {
        reservations.removeIf(reservation -> Objects.equals(reservation.getId(), id));
    }
}
