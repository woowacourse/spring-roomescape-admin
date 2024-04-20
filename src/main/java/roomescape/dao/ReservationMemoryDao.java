package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationMemoryDao implements ReservationDao {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
