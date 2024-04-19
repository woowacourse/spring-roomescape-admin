package roomescape.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {
    private static final long START_ID = 0;
    private static final AtomicLong atomicLong = new AtomicLong(START_ID);

    private final Map<Long, Reservation> reservations = new HashMap<>();

    public List<ReservationResponse> findAllReservations() {
        return reservations.values().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        long incrementId = atomicLong.incrementAndGet();

        Reservation reservation = new Reservation(
                incrementId,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.put(incrementId, reservation);
        return ReservationResponse.from(reservation);
    }

    public boolean delete(long reservationId) {
        if (!reservations.containsKey(reservationId)) {
            return false;
        }
        reservations.remove(reservationId);
        return true;
    }
}
