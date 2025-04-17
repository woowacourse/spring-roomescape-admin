package roomescape.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.Reservation;
import roomescape.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private static final AtomicLong autoIncrement = new AtomicLong(0);
    private static final List<Reservation> REPOSITORY = new ArrayList<>();

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(REPOSITORY);
    }

    @Override
    public Reservation save(Reservation reservation) {
        REPOSITORY.add(reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return REPOSITORY.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findFirst();
    }

    @Override
    public void remove(Reservation reservation) {
        REPOSITORY.remove(reservation);
    }

    public static void clear() {
        autoIncrement.set(0);
        REPOSITORY.clear();
    }
}
