package roomescape.console.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

public class MemoryReservationDao implements ReservationDao {

    private final AtomicLong index;
    private final List<Reservation> reservations;

    public MemoryReservationDao() {
        index = new AtomicLong(0);
        reservations = new ArrayList<>();
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = index.incrementAndGet();
        Reservation saveReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(saveReservation);

        return saveReservation;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @Override
    public void delete(Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        reservations.remove(findReservation);
    }
}
