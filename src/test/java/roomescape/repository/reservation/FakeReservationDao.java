package roomescape.repository.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;

public class FakeReservationDao implements ReservationDao {

    final List<Reservation> reservations;
    long id = 1L;

    public FakeReservationDao(List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void addAll(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            save(reservation);
        }
    }

    @Override
    public void save(Reservation reservation) {
        reservation.setId(id++);
        reservations.add(reservation);
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return reservations.stream()
            .filter(reservation -> reservation.getId() == id)
            .findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public boolean deleteById(long id) {
        return reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
