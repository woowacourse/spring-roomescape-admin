package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public long generateId() {
        return index.getAndIncrement();
    }

    public List<Reservation> findAll() {
        return reservations.stream()
                .toList();
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.sameId(id))
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation findReservation = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));

        reservations.remove(findReservation);
    }
}
