package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation reservationWithId = reservation.createWithId(nextId.getAndIncrement());
        reservations.add(reservationWithId);
        return reservationWithId;
    }

    @Override
    public void removeById(int id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
