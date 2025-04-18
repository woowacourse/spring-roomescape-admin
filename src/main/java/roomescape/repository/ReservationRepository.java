package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return reservations;
    }

    public Long add(final String name, final LocalDate date, final LocalTime time) {
        Reservation reservation = new Reservation(index.getAndIncrement(),
                name,
                date,
                time
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    public void remove(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        reservations.remove(reservation);
    }
}
