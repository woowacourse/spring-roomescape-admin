package roomescape.reservationTime.repository;

import org.springframework.http.HttpStatus;
import roomescape.globalException.CustomException;
import roomescape.reservationTime.domain.ReservationTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final AtomicLong id = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public ReservationTime findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "존재하지 않는 예약 id로 요청하였습니다."));
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        ReservationTime savedReservationTime = new ReservationTime(id.getAndIncrement(), reservationTime.getStartAt());
        reservationTimes.add(savedReservationTime);
        return savedReservationTime;
    }

    @Override
    public void delete(Long id) {
        ReservationTime reservationTime = findByIdOrThrow(id);
        reservationTimes.remove(reservationTime);
    }

    private Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }
}
