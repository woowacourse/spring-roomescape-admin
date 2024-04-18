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

    public void delete(long reservationId) {
        if (!reservations.containsKey(reservationId)) {
            throw new IllegalArgumentException("해당 예약을 찾을 수 없습니다. 유효한 에약을 입력해 주세요.");
        }
        reservations.remove(reservationId);
    }
}
