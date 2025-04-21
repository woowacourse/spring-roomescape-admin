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
    public void deleteById(final long id) {
        Reservation found = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[ERROR] 예약번호 %d번은 존재하지 않습니다.", id)));
        reservations.remove(found);
    }

    @Override
    public void clear() {
        reservations.clear();
    }

}
