package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import roomescape.domain.Reservation;

public class ReservationMemoryDao implements ReservationDao {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
