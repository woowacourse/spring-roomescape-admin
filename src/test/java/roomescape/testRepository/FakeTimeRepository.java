package roomescape.testRepository;

import java.util.ArrayList;
import java.util.List;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeRepository;

public class FakeTimeRepository implements TimeRepository {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    private Long index = 1L;

    @Override
    public Long save(ReservationTime reservationTime) {
        ReservationTime timeWithId = ReservationTime.withId(index++, reservationTime);
        reservationTimes.add(timeWithId);
        return index;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 ReservationTime이 존재하지 않습니다"));
    }

    @Override
    public void deleteById(Long id) {
        ReservationTime reservationTime = findById(id);
        reservationTimes.remove(reservationTime);
    }
}
