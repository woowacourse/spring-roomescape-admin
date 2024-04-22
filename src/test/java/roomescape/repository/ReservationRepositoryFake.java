package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.repository.reservation.ReservationRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepositoryFake implements ReservationRepository {
    private final AtomicLong index = new AtomicLong(1);
    private final Map<Long, Reservation> repository = new ConcurrentHashMap<>();

    @Override
    public Reservation insert(final Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation);
        repository.put(newReservation.getId(), newReservation);

        return newReservation;
    }

    @Override
    public List<Reservation> findAll() {
        return repository.values().stream().toList();
    }

    @Override
    public int deleteById(final Long id) {
        if (repository.containsKey(id)) {
            repository.remove(id);
            return 1;
        }
        return 0;
    }
}
