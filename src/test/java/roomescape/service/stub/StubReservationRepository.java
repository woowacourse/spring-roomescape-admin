package roomescape.service.stub;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.repository.ReservationRepository;

public class StubReservationRepository implements ReservationRepository {

    private final List<Reservation> data = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    public StubReservationRepository(final Reservation... inputReservations) {
        data.addAll(List.of(inputReservations));
        long maxId = data.stream()
                .mapToLong(Reservation::getId)
                .max()
                .orElse(0L);
        atomicLong.set(maxId);
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(data);
    }

    @Override
    public boolean existsByDateAndTime(final LocalDate date, final LocalTime time) {
        return data.stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().getStartAt()
                        .equals(time));
    }

    @Override
    public Reservation save(final String name, final LocalDate date, final Long timeId, final LocalTime time) {
        long newId = atomicLong.incrementAndGet();
        Reservation reservation = new Reservation(
                newId,
                name,
                date,
                new ReservationTime(timeId, time)
        );
        data.add(reservation);
        return reservation;
    }

    @Override
    public void remove(final Long id) {
        data.removeIf(r -> r.getId().equals(id));
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        return data.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
}
