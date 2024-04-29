package roomescape.fixture;

import java.util.ArrayList;
import java.util.List;
import roomescape.data.vo.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();


    @Override
    public long add(final ReservationTime time) {
        reservationTimes.add(new ReservationTime(reservationTimes.size() + 1, time.getStartAt()));
        return reservationTimes.size();
    }

    @Override
    public List<ReservationTime> getAll() {
        return reservationTimes;
    }

    @Override
    public ReservationTime get(long id) {
        final var reservationTime = reservationTimes.get((int) (id - 1));
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void remove(final long id) {
        reservationTimes.remove((int) id - 1);
    }
}
