package roomescape.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class FakeReservationDAOImpl implements ReservationDAO {

    final List<Reservation> reservations = new ArrayList<>();
    final AtomicLong atomicLong = new AtomicLong(1L);

    @Override
    public List<Reservation> findAllReservation() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Long insertReservation(final Reservation reservation) {
        final long id = atomicLong.getAndIncrement();
        reservation.setId(id);
        reservations.add(reservation);
        return id;
    }

    @Override
    public int deleteReservationById(final Long id) {
        final int beforeSize = reservations.size();
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        final int afterSize = reservations.size();
        return beforeSize - afterSize;
    }

    @Override
    public boolean existsById(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getId().equals(id));
    }
}
