package roomescape.console.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationRepository;

public class ReservationMemoryDao implements ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

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
        Long id = counter.incrementAndGet();
        Reservation savedReservation = new Reservation(
                id, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
