package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation entity = Reservation.toEntity(nextId.getAndIncrement(), reservation);
        reservations.add(entity);
        return entity;
    }

    @Override
    public void removeById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
