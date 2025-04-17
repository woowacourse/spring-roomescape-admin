package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

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
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findFirst();
    }

    @Override
    public void remove(Reservation reservation) {
        REPOSITORY.remove(reservation);
    }
}
