package roomescape.reservation.repository.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.repository.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    AtomicLong atomicLong = new AtomicLong(1L);
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation insertReservation(Reservation reservation) {
        Reservation newReservation = new Reservation(atomicLong.getAndIncrement(), reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public boolean deleteReservationById(long id) {
        return reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
