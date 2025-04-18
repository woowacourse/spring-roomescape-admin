package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final ConcurrentMap<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return reservations.keySet().stream()
                .map(reservations::get)
                .toList();
    }

    public Reservation add(final ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRequest.toEntity(index.getAndIncrement());
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new InvalidReservationException();
        }
        reservations.remove(id);
    }
}
