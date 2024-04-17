package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class ReservationRepository {

    private final List<Reservation> reservations;
    private final AtomicLong index;

    public ReservationRepository() {
        this.reservations = new ArrayList<>();
        this.index = new AtomicLong(1);
    }


    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(reservation);
    }

    public AtomicLong getIndex() {
        return index;
    }
}
