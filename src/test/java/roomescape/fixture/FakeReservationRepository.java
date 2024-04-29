package roomescape.fixture;

import java.util.ArrayList;
import java.util.List;
import roomescape.data.vo.Reservation;
import roomescape.repository.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation get(final long id) {
        return reservations.get((int) id);
    }

    @Override
    public long add(Reservation reservation) {
        reservations.add(reservation);
        return reservations.indexOf(reservation) + 1;
    }

    @Override
    public List<Reservation> getAll() {
        return reservations;
    }

    @Override
    public void remove(long id) {
        reservations.remove(reservations.get((int) (id - 1)));
    }
}
