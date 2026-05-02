package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private AtomicLong id = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

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
    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(
                id.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
