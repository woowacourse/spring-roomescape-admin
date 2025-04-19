package roomescape.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class RoomescapeRepositoryImpl implements RoomescapeRepository {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public RoomescapeRepositoryImpl(final List<Reservation> reservations) {
        this.reservations = new CopyOnWriteArrayList<>(reservations);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation saveReservation(final Reservation reservation) {
        if (existsSameReservation(reservation)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약시간입니다.");
        }
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

    private boolean existsSameReservation(final Reservation reservation) {
        return reservations.stream()
                .anyMatch(reservation::isDuplicateReservation);
    }
}
