package roomescape.reservation.infrastructure;

import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.infrastructure.entity.ReservationEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private static final int START_ID_NUMBER = 1;

    private final AtomicLong idGenerator = new AtomicLong(START_ID_NUMBER);
    private final Map<Long, ReservationEntity> reservations = new HashMap<>();

    @Override
    public Optional<Reservation> findById(final long id) {
        return Optional.ofNullable(reservations.get(id))
                .map(ReservationEntity::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Reservation save(final Reservation reservation) {
        final ReservationEntity reservationEntity = ReservationEntity.from(reservation);
        reservationEntity.setId(idGenerator.getAndIncrement());
        reservations.put(reservationEntity.getId(), reservationEntity);

        return reservationEntity.toDomain();
    }

    @Override
    public void deleteById(final long id) {
        reservations.remove(id);
    }
}
