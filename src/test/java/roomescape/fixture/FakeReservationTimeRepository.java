package roomescape.fixture;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import roomescape.data.vo.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> fake = new ArrayList<>(List.of(new ReservationTime(1, LocalTime.of(1, 1))));


    @Override
    public long add(final ReservationTime time) {
        fake.add(new ReservationTime(fake.size() + 1, time.getStartAt()));
        return fake.size();
    }

    @Override
    public List<ReservationTime> getAll() {
        return fake;
    }

    @Override
    public ReservationTime get(long id) {
        final var reservationTime = fake.get((int) (id - 1));
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void remove(final long id) {
        fake.remove((int) id - 1);
    }
}
