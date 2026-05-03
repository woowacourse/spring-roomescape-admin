package roomescape.fixture;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class TestReservationDao implements ReservationDao {

    private final List<Reservation> reservations;
    private final AtomicLong autoIncrement = new AtomicLong(0);

    public TestReservationDao(List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    @Override
    public List<Reservation> selectAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation insert(Reservation reservation) {
        Reservation reservationEntity = new Reservation(
                autoIncrement.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        reservations.add(reservationEntity);
        return reservationEntity;
    }

    @Override
    public boolean delete(Long id) {
        return reservations.removeIf(
                reservation -> Objects.equals(reservation.getId(), id)
        );
    }

}
