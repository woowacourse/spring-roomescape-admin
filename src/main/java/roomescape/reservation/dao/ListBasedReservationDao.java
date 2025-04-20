package roomescape.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.Reservation;

public class ListBasedReservationDao implements ReservationDao {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getCustomerName(),
                reservation.getReservationDate(), reservation.getReservationTime());
        reservations.add(newReservation);
        return newReservation;
    }

    public boolean removeById(long id) {
        return reservations.removeIf(reservation -> reservation.isIdEquals(id));
    }
}
