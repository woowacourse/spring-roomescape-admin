package roomescape.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.domain.Reservation;

public class FakeReservationDao implements ReservationDao {

    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;

    public FakeReservationDao() {
        this.reservations = Collections.synchronizedList(new ArrayList<>());
        this.atomicLong = new AtomicLong(1L);
    }

    @Override
    public Reservation insert(Reservation requestReservation) {
        Reservation reservation = new Reservation(atomicLong.getAndIncrement(), requestReservation);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public Reservation findById(long id) {
        return reservations.get((int)(id - 1));
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void delete(long id) {
        if (reservations.size() < id) {
            throw new NoSuchElementException("데이터베이스에 해당 id가 존재하지 않습니다.");
        }
        Reservation targetReservation = findById(id);
        reservations.remove(targetReservation);
    }
}
