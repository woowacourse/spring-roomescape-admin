package fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.reservation.Reservation;
import roomescape.reservation.dao.ReservationDao;

public class FakeReservationDao implements ReservationDao {
    private List<Reservation> reservations = new ArrayList<>();
    private long index = 1L;

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index++, reservation.getCustomerName(),
                reservation.getReservationDate(), reservation.getReservationTime());
        reservations.add(newReservation);
        return newReservation;
    }

    public boolean removeById(long id) {
        return reservations.removeIf(reservation -> reservation.isIdEquals(id));
    }
}
