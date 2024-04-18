package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation save(Reservation reservation) {
        reservation.initializeId(index.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
        return reservations.get(reservation.getId());
    }

    public List<Reservation> findAllByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.hasSameDateTime(date, time))
                .toList();
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
