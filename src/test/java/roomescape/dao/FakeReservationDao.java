package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class FakeReservationDao implements ReservationDao {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation persisted = new Reservation(
                sequence.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(persisted);
        return persisted;
    }

    @Override
    public int deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id)) ? 1 : 0;
    }

    @Override
    public boolean existsByTimeId(Long timeId) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getTime().getId().equals(timeId));
    }
}
