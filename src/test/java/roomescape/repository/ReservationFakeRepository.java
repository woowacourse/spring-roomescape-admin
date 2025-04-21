package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.Reservation;
import roomescape.dto.CreateReservationRequest;

public class ReservationFakeRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public Optional<Reservation> findById(final long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public long save(CreateReservationRequest request) {
        final var reservation = request.toReservation(index.getAndIncrement());
        reservations.put(reservation.id(), reservation);
        return reservation.id();
    }

    public boolean removeById(long id) {
        Reservation removed = reservations.remove(id);
        return removed != null;
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations.values());
    }
}
