package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1L);

    private final List<Reservation> reservations;

    public ReservationInMemoryRepository() {
        reservations = new ArrayList<>();
    }

    public ReservationInMemoryRepository(List<Reservation> reservations) {
        this.reservations = reservations;
    }
 
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Reservation save(ReservationRequestDto requestDto) {
        Long id = ATOMIC_LONG.getAndIncrement();
        Reservation newReservation = requestDto.toEntity(id);
        reservations.add(newReservation);
        return newReservation;
    }

    public void delete(Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(reservation);
    }
}
