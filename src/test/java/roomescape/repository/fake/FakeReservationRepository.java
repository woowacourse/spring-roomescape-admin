package roomescape.repository.fake;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.Reservation;
import roomescape.Reservations;
import roomescape.repository.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {
    private final Reservations reservations = new Reservations();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public Reservation createReservation(Reservation reservation) {
        Reservation createdReservation = Reservation.generateWithPrimaryKey(reservation, id.getAndIncrement());
        reservations.add(createdReservation);
        return createdReservation;
    }

    @Override
    public List<Reservation> readReservations() {
        return reservations.getReservations();
    }

    @Override
    public void deleteReservation(Long id) {
        reservations.remove(id);
    }
}
