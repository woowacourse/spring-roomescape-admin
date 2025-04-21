package roomescape.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class RoomescapeRepositoryImpl implements RoomescapeRepository {

    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation saveReservation(final Reservation reservation) {
        Reservation saved = reservation.toEntity(index.getAndIncrement());
        reservations.add(saved);
        return saved;
    }

    @Override
    public int deleteById(final long id) {
        List<Reservation> candidates = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .toList();
        reservations.removeAll(candidates);
        return candidates.size();
    }

    @Override
    public void clear() {
        reservations.clear();
    }

}
