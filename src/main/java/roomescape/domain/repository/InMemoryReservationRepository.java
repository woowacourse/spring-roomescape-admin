package roomescape.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation(id.getAndIncrement());
        reservations.add(reservation);

        return reservation;
    }

    @Override
    public boolean deleteReservation(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
