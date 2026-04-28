package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong id = new AtomicLong();

    public InMemoryReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public ReservationResponse insert(ReservationRequest request) {
        Reservation reservation = new Reservation(
                id.incrementAndGet(),
                request.name(),
                request.date().atTime(request.time())
        );
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(r -> r.hasId(id));
    }
}
