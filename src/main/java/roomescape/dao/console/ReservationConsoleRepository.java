package roomescape.dao.console;

import roomescape.dao.ReservationRepository;
import roomescape.entity.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationConsoleRepository implements ReservationRepository {

    private final AtomicLong idIndex = new AtomicLong(1);
    private final ConcurrentMap<Long, Reservation> reservations = new ConcurrentHashMap<>();

    @Override
    public Reservation createReservation(Reservation reservation) {
        Long id = idIndex.getAndIncrement();
        Reservation createdReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
        reservations.put(id, createdReservation);
        return createdReservation;
    }

    @Override
    public List<Reservation> findReservations() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public void removeReservation(Long id) {
        reservations.remove(id);
    }
}
