package roomescape.reservation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import roomescape.time.Time;

public class FakeReservationDao implements ReservationDao {

    private static final Time DUMMY_TIME = new Time(1L, LocalTime.of(12, 40));

    private Long NEXT_ID = 1L;
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Long> invokeDeleteId = new ArrayList<>();

    @Override
    public Long saveReservation(final Reservation reservation, final Long writeId) {
        final Reservation writedReservation = new Reservation(
                NEXT_ID++,
                reservation.name(),
                reservation.date(),
                DUMMY_TIME
        );
        reservations.add(writedReservation);
        return writedReservation.id();
    }

    @Override
    public List<Reservation> findAllReservation() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation findReservationById(final Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void deleteReservationById(final long id) {
        reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .ifPresent(reservation -> reservations.remove(reservation));
        invokeDeleteId.add(id);
    }

    public boolean isInvokeDeleteById(final Long id) {
        return invokeDeleteId.stream()
                .anyMatch(value -> Objects.equals(value, id));
    }
}
