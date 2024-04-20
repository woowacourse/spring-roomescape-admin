package roomescape.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> repository = new HashMap<>();
    private final AtomicLong index = new AtomicLong();

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return repository.values().stream().toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long id = index.incrementAndGet();
        Reservation savedReservation = new Reservation(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
        repository.put(id, savedReservation);
        return savedReservation;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Reservation> findReservation = findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        repository.remove(id);
    }
}
