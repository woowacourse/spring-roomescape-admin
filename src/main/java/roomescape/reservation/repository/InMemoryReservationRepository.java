package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.domain.Reservation;

public class InMemoryReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    private static final long DELTA = 1;

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservations -> reservations.getId().equals(id)).findAny();
    }

    @Override
    public Long save(Reservation reservation) {
        if (!findById(reservation.getId()).isEmpty()) {
            throw new IllegalStateException("중복된 예약 id가 존재합니다.");
        }
        reservations.add(reservation);
        return reservation.getId();
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예약입니다."));

        reservations.remove(reservation);
    }

    public Long generateId() {
        return index.addAndGet(DELTA);
    }
}
