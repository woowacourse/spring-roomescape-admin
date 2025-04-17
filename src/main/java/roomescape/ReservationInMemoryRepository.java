package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private static final AtomicLong AUTO_INCREMENT = new AtomicLong(0);
    private static final List<Reservation> REPOSITORY = new ArrayList<>();

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(REPOSITORY);
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservation.updateId(AUTO_INCREMENT.getAndIncrement());
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
}
