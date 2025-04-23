package roomescape.repository.fake;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {
    private final ReservationTimes reservationTimes = new ReservationTimes();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        ReservationTime createdReservationTime = ReservationTime.generateWithPrimaryKey(reservationTime, id.getAndIncrement());
        reservationTimes.add(createdReservationTime);
        return createdReservationTime;
    }

    @Override
    public List<ReservationTime> readReservationTimes() {
        return reservationTimes.getReservationTimes();
    }

    @Override
    public ReservationTime readReservationTime(Long id) {
        return reservationTimes.getReservationTimes().stream()
                .filter(reservationTime -> reservationTime.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 예약 시간 ID를 찾을 수 없습니다."));
    }

    @Override
    public void deleteReservationTime(Long id) {
        reservationTimes.remove(id);
    }
}
