package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Reservation;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public RowMapper<Reservation> createRowMapper() {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            reservation = new Reservation(
                    index.getAndIncrement(),
                    reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime()
            );
        }
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
