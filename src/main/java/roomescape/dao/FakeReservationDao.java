package roomescape.dao;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FakeReservationDao implements ReservationDao {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return Collections.synchronizedList(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public boolean existsById(long id) {
        return reservations.stream().anyMatch(reservation -> reservation.getId() == id);
    }

    @Override
    public void deleteById(long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
