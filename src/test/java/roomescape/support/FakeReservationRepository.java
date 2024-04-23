package roomescape.support;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private long id = 1;

    @Override
    public Reservation save(Reservation reservation) {
        Reservation updatedReservation = reservation.updateId(id++);
        reservations.put(updatedReservation.getId(), updatedReservation);
        return updatedReservation;
    }

    @Override
    public boolean existsByReservationDateTime(LocalDate date, long timeId) {
        return reservations.values().stream()
                .anyMatch(r -> r.getDate().equals(date) && r.getTimeId() == timeId);
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(r -> reservations.remove(id));
    }
}
