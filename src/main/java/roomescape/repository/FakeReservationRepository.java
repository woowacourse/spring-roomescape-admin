package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakeReservationRepository implements ReservationRepository {

    private static final int START_ID_NUMBER = 1;

    private final AtomicLong idGenerator = new AtomicLong(START_ID_NUMBER);

    private final Map<Long, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation findById(final long id) {
        return reservations.get(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public Reservation save(final Reservation reservation) {
        reservation.setId(idGenerator.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public void deleteById(final long id) {
        reservations.remove(id);
    }

    @Override
    public boolean selectByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values().stream()
                .anyMatch(
                        reservation -> reservation.getDate().isEqual(date) ||reservation.getTime().equals(time));
    }
}
