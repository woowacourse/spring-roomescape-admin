package roomescape.console.dao;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationMemoryDao implements ReservationDao {
    private static ReservationMemoryDao instance;

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    private ReservationMemoryDao() {
    }

    public static ReservationMemoryDao getInstance() {
        if (instance == null) {
            instance = new ReservationMemoryDao();
        }
        return instance;
    }

    @Override
    public ReservationId create(Reservation reservation) {
        ReservationTimeMemoryDao reservationTimeMemoryDao = ReservationTimeMemoryDao.getInstance();
        ReservationTime reservationTime = reservationTimeMemoryDao.findById(reservation.getTime().getId()).orElseThrow(() -> new IllegalArgumentException("time id 가 존재하지 않습니다. (id : " + reservation.getTime().getId() + ")"));
        Reservation newReservation = new Reservation(id.getAndIncrement(), reservation.getName(), reservation.getDate(), reservationTime);
        reservations.add(newReservation);
        return new ReservationId(newReservation.getId());
    }

    @Override
    public Optional<Reservation> findAnyByTimeId(long timeId) {
        return reservations.stream().filter(reservation -> reservation.getTime().getId() == timeId).findFirst();
    }

    @Override
    public Reservation findById(long id) {
        return reservations.stream().filter(reservation -> reservation.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id 입니다. (id : " + id + ")"));
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void delete(long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
