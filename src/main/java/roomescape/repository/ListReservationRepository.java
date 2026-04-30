package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListReservationRepository implements ReservationRepository {
    private static final AtomicLong idCursor = new AtomicLong(1);
    private static final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservation.bindId(idCursor.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public void delete(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
