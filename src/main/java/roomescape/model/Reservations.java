package roomescape.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public class Reservations {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong atomicLong = new AtomicLong();

    public ReservationResponse createReservation(ReservationRequest request) {
        long id = atomicLong.incrementAndGet();
        Reservation reservation = request.toEntity();
        reservations.put(id, reservation);

        return ReservationResponse.toDto(id, reservation);
    }

    public void deleteReservation(Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return;
        }
        throw new IllegalArgumentException("해당하는 예약이 존재하지 않습니다.");
    }

    public List<ReservationResponse> getReservations(){
        return reservations.entrySet().stream()
                .map(entry -> ReservationResponse.toDto(entry.getKey(), entry.getValue()))
                .toList();
    }
}
