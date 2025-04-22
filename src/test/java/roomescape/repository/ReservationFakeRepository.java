package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.model.TimeSlot;

public class ReservationFakeRepository implements ReservationRepository {

    public static final TimeSlot FIXED_TIME_SLOT
        = new TimeSlot(1L, LocalTime.of(10, 0));

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public Optional<Reservation> findById(final long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public long save(CreateReservationRequest request) {
        TimeSlot timeSlot = defineTimeSlot(request);
        final var reservation = request.toReservation(index.getAndIncrement(), timeSlot);
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

    private TimeSlot defineTimeSlot(final CreateReservationRequest request) {
        if (request.timeSlotId() != null) {
            return FIXED_TIME_SLOT;
        }
        return null;
    }
}
