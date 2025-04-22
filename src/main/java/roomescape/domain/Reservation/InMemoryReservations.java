package roomescape.domain.Reservation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime.ReservationTime;
import roomescape.domain.ReservationTime.ReservationTimes;
import roomescape.dto.request.ReservationCreateRequest;

public class InMemoryReservations implements Reservations {

    private final List<Reservation> reservations;
    private final ReservationTimes reservationTimes;
    private final AtomicLong index = new AtomicLong(1);

    public InMemoryReservations(final List<Reservation> reservations, final ReservationTimes reservationTimes) {
        this.reservations = reservations;
        this.reservationTimes = reservationTimes;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public long create(final ReservationCreateRequest reservationCreateRequest) {
        ReservationTime reservationTime = reservationTimes.findById(reservationCreateRequest.timeId());
        Reservation reservation = new Reservation(index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationTime
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    @Override
    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.isEqualId(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        reservations.remove(reservation);
    }
}
