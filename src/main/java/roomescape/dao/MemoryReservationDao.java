package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

public class MemoryReservationDao implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong increment = new AtomicLong(1);

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation add(final Reservation entity) {
        Reservation newReservation = new Reservation(increment.getAndIncrement(), entity);
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.isEqualId(id));
    }

    public boolean existReservation(Long id) {
          return reservations.stream()
                  .anyMatch(reservation -> reservation.isEqualId(id));
    }
}
