package roomescape.console.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.domain.Reservation;
import roomescape.core.repository.ReservationRepository;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public MemoryReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(
                index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());

        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    @Override
    public void deleteAll() {
        reservations.clear();
    }

    @Override
    public Boolean existByTimeId(Long timeId) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getTimeId().equals(timeId));
    }
}
