package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationInfo;
import roomescape.entity.ReservationEntity;
import roomescape.repository.ReservationRepository;

@Repository
public class MemoryReservationDao implements ReservationRepository {

    private final List<ReservationEntity> reservations = new ArrayList<>();
    private final AtomicLong increment = new AtomicLong(1);

    public ReservationEntity add(final ReservationInfo reservationInfo) {
        ReservationEntity newReservation = new ReservationEntity(increment.getAndIncrement(), reservationInfo);
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
