package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.domain.Reservation;

public class ListReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isIdEqualTo(id))
                .findFirst();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.isIdNull()) {
            reservation.setId(index.getAndIncrement());
        }
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        findById(id)
                .map(reservations::remove)
                .orElseThrow(IllegalStateException::new);
    }
}
