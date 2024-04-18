package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private static final long MAX_RESERVATION_PER_TIME = 4;

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation save(Reservation reservation) {
        validateDateTime(reservation);
        reservation.initializeId(index.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
        return reservations.get(reservation.getId());
    }

    private void validateDateTime(Reservation newReservation) {
        long reservationCount = reservations.values()
                .stream()
                .filter(reservation -> reservation.hasSameDateTime(newReservation))
                .count();
        if (reservationCount >= MAX_RESERVATION_PER_TIME) {
            throw new IllegalArgumentException("해당 시간대에 예약이 모두 찼습니다. (최대 4팀)");
        }
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
