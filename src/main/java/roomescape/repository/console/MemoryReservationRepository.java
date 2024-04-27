package roomescape.repository.console;

import roomescape.domain.reservation.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {
    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, Reservation> reservations;

    public MemoryReservationRepository(Map<Long, Reservation> reservations) {
        this.reservations = reservations;
    }

    public MemoryReservationRepository() {
        this(new HashMap<>());
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation createdReservation = new Reservation(counter.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.put(createdReservation.getId(), createdReservation);
        return createdReservation;
    }

    @Override
    public void delete(Long id) {
        reservations.remove(id);
    }

    @Override
    public List<Reservation> findAllByDateTime(Reservation target) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.hasSameReservationTime(target))
                .toList();
    }
}
