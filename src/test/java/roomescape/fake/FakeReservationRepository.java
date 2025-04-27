package roomescape.fake;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Long save(final Reservation reservation) {
        final Long generatedId = sequence.getAndIncrement();
        final Reservation savedReservation = new Reservation(
                generatedId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        reservations.put(generatedId, savedReservation);

        return generatedId;
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalStateException("Reservation with id " + id + " does not exist");
        }

        reservations.remove(id);
    }

    public void clear() {
        reservations.clear();
        sequence.set(1);
    }
}
