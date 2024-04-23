package roomescape.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @Override
    public Reservation createReservation(Reservation requestReservation) {
        Reservation responseReservation = new Reservation(
                id.getAndIncrement(),
                requestReservation.getName(),
                requestReservation.getDate(),
                requestReservation.getTime());
        reservations.add(responseReservation);

        return responseReservation;
    }

    @Override
    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
