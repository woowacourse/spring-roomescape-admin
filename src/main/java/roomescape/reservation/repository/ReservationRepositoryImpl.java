package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return reservations.stream()
                .toList();
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        Reservation reservationEntity = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(reservationEntity);
        return reservationEntity;
    }

    public void deleteById(Long id) {
        Reservation findReservation = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));

        reservations.remove(findReservation);
    }
}
