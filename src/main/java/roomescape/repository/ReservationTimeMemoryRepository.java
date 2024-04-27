package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.ReservationTime;

public class ReservationTimeMemoryRepository implements ReservationTimeRepository {

    private static final List<ReservationTime> reservationTimes = new ArrayList<>();
    private static final AtomicLong index = new AtomicLong(0L);
    @Override
    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimes;
    }

    @Override
    public ReservationTime findReservationById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("아이디 : " + id + "에 해당하는 예약시간이 없습니다."));
    }

    @Override
    public ReservationTime addReservationTime(ReservationTime reservationTimeDto) {
        ReservationTime reservationTime = new ReservationTime(index.incrementAndGet(), reservationTimeDto.getStartAt());
        reservationTimes.add(reservationTime);
        return reservationTime;
    }

    @Override
    public void deleteReservationTime(long id) {
        ReservationTime findReservationTime = reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("아이디 : " + id + "에 해당하는 예약시간이 없습니다."));
        reservationTimes.remove(findReservationTime);
    }
}
