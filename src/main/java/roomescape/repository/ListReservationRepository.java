package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListReservationRepository implements ReservationRepository {
    private static AtomicLong idCursor = new AtomicLong(1);
    private static List<Reservation> reservations = new Vector<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservation.bindId(idCursor.getAndAdd(1));
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
