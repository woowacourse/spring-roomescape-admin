package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationRepository implements ReservationRepository {

    private List<Reservation> reservations;
    AtomicLong reservationId = new AtomicLong(1);

    public FakeReservationRepository(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        Reservation newReservation = new Reservation(reservationId.getAndIncrement(), reservation.name(), reservation.date(), reservation.time());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException());

        reservations.remove(deleteReservation);
    }
}
