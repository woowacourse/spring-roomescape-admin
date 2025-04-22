package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.repository.ReservationRepository;

public class StubReservationRepository implements ReservationRepository {

    private final boolean existsReservation;
    private final Reservation createResult;
    private final List<Reservation> reservations;

    public StubReservationRepository(boolean existsReservation, Reservation createResult,
                                     List<Reservation> reservations) {
        this.existsReservation = existsReservation;
        this.createResult = createResult;
        this.reservations = reservations;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public boolean existsByDateAndTime(final LocalDate date, final LocalTime time) {
        return existsReservation;
    }

    @Override
    public Reservation save(final String name, final LocalDate date, final LocalTime time) {
        return createResult;
    }

    @Override
    public void remove(final Long id) {
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        return Optional.empty();
    }
}
