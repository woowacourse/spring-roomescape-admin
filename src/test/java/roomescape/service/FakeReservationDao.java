package roomescape.service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationAddRequest;
import roomescape.repository.ReservationDao;

public class FakeReservationDao implements ReservationDao {

    Map<Long, Reservation> reservations;
    AtomicLong atomicLong = new AtomicLong(0);

    FakeReservationDao() {
        this.reservations = new HashMap<>();
    }

    FakeReservationDao(List<Reservation> reservations) {
        this.reservations = new HashMap<>();
        for (int i = 0; i < reservations.size(); i++) {
            this.reservations.put(reservations.get(i).getId(), reservations.get(i));
        }
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    //todo: ReservationTime 생성하는 부분 dao참조하는 것으로 변경
    @Override
    public Reservation insert(ReservationAddRequest reservationAddRequest) {
        Long id = atomicLong.incrementAndGet();
        Reservation reservation = new Reservation(id, reservationAddRequest.getName(), reservationAddRequest.getDate(),
                new ReservationTime(1L,
                        LocalTime.of(10, 0)));
        reservations.put(id, reservation);
        return reservation;
    }

    @Override
    public int deleteById(Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return 1;
        }
        return 0;
    }
}
