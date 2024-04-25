package roomescape.fake;

import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationDao implements ReservationDao {

    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;

    public FakeReservationDao() {
        reservations = new ArrayList<>();
        atomicLong = new AtomicLong(1L);
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Optional<Reservation> findById(long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == reservationId)
                .findAny();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(
                atomicLong.getAndIncrement(),
                reservation.getNameValue(),
                reservation.getDate(),
                reservation.getTime());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(long reservationId) {
        reservations.removeIf(reservation -> reservation.getId() == reservationId);
    }
}
