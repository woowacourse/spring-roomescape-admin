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
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        // TODO repository에서 객체 생성의 책임을 부여하는 것은 과도하다고 생각함.
//        Reservation reservationEntity = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation findReservation = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));

        reservations.remove(findReservation);
    }
}
