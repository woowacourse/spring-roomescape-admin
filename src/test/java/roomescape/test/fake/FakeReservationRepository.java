package roomescape.test.fake;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

public class FakeReservationRepository extends ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public FakeReservationRepository() {
        super(null);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Optional<Reservation> findById(long id) {
        if (!reservations.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(reservations.get(id));
    }

    @Override
    public Reservation add(String name, LocalDate date, LocalTime time) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), name, date, time);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약이 없습니다: " + id);
        }
        reservations.remove(id);
    }
}
