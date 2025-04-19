package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final AtomicLong index;
    private final List<Reservation> reservations;

    public Reservations() {
        this.index = new AtomicLong(1);
        this.reservations = new ArrayList<>();
    }

    public Reservation save(Reservation reservation) {
        Reservation entity = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(entity);
        return entity;
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
    }

    public void removeById(long id) {
        Reservation found = findById(id);
        reservations.remove(found);
    }
}
