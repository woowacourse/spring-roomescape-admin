package roomescape.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private static final AtomicLong AUTO_INCREMENT = new AtomicLong(1);
    private static final List<Reservation> REPOSITORY = new ArrayList<>();

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(REPOSITORY);
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservation.updateId(AUTO_INCREMENT.getAndIncrement());
        REPOSITORY.add(reservation);
        return Reservation.deepCopyOf(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return REPOSITORY.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findFirst()
                .map(Reservation::deepCopyOf);
    }

    @Override
    public void remove(Long id) {
        REPOSITORY.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findFirst()
                .ifPresent(REPOSITORY::remove);
    }
}
