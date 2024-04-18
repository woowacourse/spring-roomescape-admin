package roomescape.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private static final AtomicLong atomicLong = new AtomicLong();

    private final Map<Long, Reservation> reservations = new HashMap<>();

    public List<Reservation> findAllReservations() {
        return reservations.values().stream()
                .toList();
    }

    public Reservation create(ReservationRequest reservationRequest) {
        long incrementId = atomicLong.incrementAndGet();

        Reservation reservation = new Reservation(
                incrementId,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.put(incrementId, reservation);
        return reservation;
    }

    public boolean delete(long reservationId) {
        if (!reservations.containsKey(reservationId)) {
            return false;
        }
        reservations.remove(reservationId);
        return true;
    }
}
