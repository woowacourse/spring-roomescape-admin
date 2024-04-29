package roomescape.console.dao;

import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationTimeMemoryDao implements ReservationTimeDao {

    private static ReservationTimeMemoryDao instance;
    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    // private 생성자
    private ReservationTimeMemoryDao() {
    }

    public static ReservationTimeMemoryDao getInstance() {
        if (instance == null) {
            instance = new ReservationTimeMemoryDao();
        }
        return instance;
    }

    @Override
    public ReservationTime create(ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(id.getAndIncrement(), reservationTime.getStartAt());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.stream().filter(reservationTime -> reservationTime.getId() == id)
                .findFirst();
    }

    @Override
    public void delete(long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId() == id);
    }
}
