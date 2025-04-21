package roomescape.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.utils.ReservationMapper;

public class FakeReservationDao implements ReservationDao {

    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;
    private final ReservationMapper reservationMapper;

    public FakeReservationDao(ReservationMapper reservationMapper) {
        this.reservations = Collections.synchronizedList(new ArrayList<>());
        this.atomicLong = new AtomicLong(1L);
        this.reservationMapper = reservationMapper;
    }

    @Override
    public Reservation insert(ReservationRequest reservationRequest) {
        Reservation reservation = reservationMapper.toReservation(reservationRequest, atomicLong.getAndIncrement());
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
