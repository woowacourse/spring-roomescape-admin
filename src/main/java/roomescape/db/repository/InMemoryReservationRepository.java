package roomescape.db.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.db.domain.Reservation;

public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong index;
    private final List<Reservation> reservations;

    public InMemoryReservationRepository() {
        this.reservations = new ArrayList<>();
        this.index = new AtomicLong(1);
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(final long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId()== id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 예약 id입니다."));

        reservations.remove(reservation);
    }

    @Override
    public long getCurrentId() {
        return index.getAndIncrement();
    }
}
