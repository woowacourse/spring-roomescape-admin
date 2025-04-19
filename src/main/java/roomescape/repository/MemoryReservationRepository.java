package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final List<ReservationEntity> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong increment = new AtomicLong(1);

    public ReservationEntity add(final Reservation reservation) {
        ReservationEntity newReservation = new ReservationEntity(increment.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteById(final Long id) {
        reservations.removeIf(reservation -> reservation.isEqualId(id));
    }

    public boolean existReservation(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isEqualId(id));
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }
}
