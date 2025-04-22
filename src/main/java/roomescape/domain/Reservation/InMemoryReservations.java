package roomescape.domain.Reservation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.request.ReservationCreateRequest;

public class InMemoryReservations implements Reservations {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public InMemoryReservations(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public long create(final ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = new Reservation(index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time()
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    @Override
    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        reservations.remove(reservation);
    }
}
