package roomescape.repository.reservationtime;

import java.util.ArrayList;
import java.util.List;
import roomescape.model.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private Long id = 1L;

    @Override
    public long save(final ReservationTime time) {
        time.setId(id);
        reservationTimes.add(time);
        return time.getId();
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public void deleteById(final Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId().equals(id));
    }

    @Override
    public ReservationTime findById(final Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 id가 존재하지 않습니다."));
    }
}
