package roomescape.reservation.dao.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

public class FakeReservationJdbcDao implements ReservationDao {
    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;

    public FakeReservationJdbcDao() {
        this.reservations = Collections.synchronizedList(new ArrayList<>());
        this.atomicLong = new AtomicLong(1L);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @Override
    public Reservation insertReservation(final Reservation notInsertedReservation) {
        Reservation reservation = new Reservation(
                atomicLong.getAndIncrement(),
                notInsertedReservation.getName(),
                notInsertedReservation.getDate(),
                notInsertedReservation.getTime());

        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void removeReservation(final long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
