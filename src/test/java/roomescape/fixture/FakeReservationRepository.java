package roomescape.fixture;

import java.util.ArrayList;
import java.util.List;
import roomescape.data.vo.Reservation;
import roomescape.repository.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private List<Reservation> fake = new ArrayList<>();

    @Override
    public Reservation get(final long id) {
        return fake.get((int) id);
    }

    @Override
    public long add(Reservation reservation) {
        fake.add(reservation);
        return fake.indexOf(reservation) + 1;
    }

    @Override
    public List<Reservation> getAll() {
        return fake;
    }

    @Override
    public void remove(long id) {
        fake.remove(fake.get((int) (id -1)));
    }
}
