package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationRequest;

public class AdminRepository {
    private static final Map<Long, Reservation> reservations = new HashMap<>();

    private final AtomicLong index = new AtomicLong(0);

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        long id = index.incrementAndGet();
        Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.put(id, reservation);
        return reservation;
    }

    public void deleteReservation(final long id) {
        reservations.remove(id);
    }

    public void deleteAll() {
        reservations.clear();
    }

    public Map<Long, Reservation> getReservations() {
        return reservations;
    }

}
